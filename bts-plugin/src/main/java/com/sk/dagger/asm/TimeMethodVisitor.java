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
//            Label mLabel0 = new Label();
//            mv.visitLabel(mLabel0);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LSTORE, 2);
            mv.visitVarInsn(ALOAD, 1);
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (needInject) {
//            // 使用ASM View Plugin查看就能写出来
//            Label label1 = new Label();
//            mv.visitLabel(label1);
//            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//            // 这个300000相当于变量名字，var300000
//            // 有一个参数 会占据一个var1，所以避免跟参数重复，所以加一个大值
//            mv.visitVarInsn(LSTORE, 300000);
//            Label label2 = new Label();
//            mv.visitLabel(label2);
//            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitVarInsn(LLOAD, 300000);
//            mv.visitVarInsn(LLOAD, 100000);
//            mv.visitInsn(LSUB);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);
//            Label label3 = new Label();
//            mv.visitLabel(label3);
//            mv.visitInsn(RETURN);
//            Label label4 = new Label();
//            mv.visitLabel(label4);

            if ((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW) {
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("方法执行耗时->" + methodName + "：");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitVarInsn(LLOAD, 2);
                mv.visitInsn(LSUB); // 两个Long类型

                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                mv.visitLdcInsn("ms");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            }
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
