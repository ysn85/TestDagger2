package com.sk.dagger.asm;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
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
            mv.visitVarInsn(LSTORE, 3); // 将获取到的值给到var3
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (needInject) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, 3); // 加载var3的值
            mv.visitInsn(LSUB); // 当前时间戳与var3进行减法运算
            mv.visitVarInsn(LSTORE, 4); // 将运算后的差值给到var4
            mv.visitVarInsn(LLOAD, 4); // 加载var4的值
            mv.visitLdcInsn(new Long(10L)); // 将10常量值压栈
            mv.visitInsn(LCMP); // 将var4与刚压栈的10常量做比较运算 var4 == 10L ? : 0 (var4 < 10L ? -1 : 1)
            Label label1 = new Label();
            mv.visitJumpInsn(IFLE, label1); // 对var4和10进行LCMP 如果<=2 则跳至end
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("方法执行耗时->" + methodName + "：");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(LLOAD, 4); // 加载var4的值
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("ms");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitLabel(label1);
            mv.visitInsn(RETURN);
            mv.visitEnd();
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);
        if (ANNOTATION_METHOD.equals(desc)) {
            needInject = true;
            autoClassVisitor.setNeedInject(true);
        }
        return annotationVisitor;
    }
}
