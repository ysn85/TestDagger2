package com.sk.dagger.asm;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class TimeMethodVisitor extends AdviceAdapter {
    public static final String ANNOTATION_METHOD = "Lcom/sk/dagger/bts_annotation/FuncTimeCost;";
    boolean needInject = false;
    private MethodVisitor mv;
    private String methodName;
    private AutoClassVisitor autoClassVisitor;

    protected TimeMethodVisitor(int api, MethodVisitor mv, int access, String methodName, String descriptor, AutoClassVisitor autoClassVisitor) {
        super(api, mv, access, methodName, descriptor);
        this.mv = mv;
        this.methodName = methodName;
        this.autoClassVisitor = autoClassVisitor;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        if (needInject) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LSTORE, 3);
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (needInject) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("方法执行耗时->" + methodName + "：");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, 3);
            mv.visitInsn(LSUB); // 两个Long类型

            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("ms");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);
        if (desc.equals(ANNOTATION_METHOD)) {
            needInject = true;
            autoClassVisitor.setNeedInject(true);
        }
        return annotationVisitor;
    }
}
