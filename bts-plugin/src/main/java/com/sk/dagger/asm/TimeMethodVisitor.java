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
            mv.visitVarInsn(LSTORE, 3); // 从操作数栈中弹出一个值（System.currentTimeMillis()返回的值），并将其存储在var3局部变量中
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (needInject) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false); // 触发System中的currentTimeMillis静态方法
            mv.visitVarInsn(LLOAD, 3); // 读取局部变量var3的值，并将其值压到操作数栈中
            mv.visitInsn(LSUB); // 从操作数栈中弹出System.currentTimeMillis()返回值和var3，将两者做差值运算，并将计算结果压入栈中
            mv.visitVarInsn(LSTORE, 4); // 从操作栈中弹出LSUB的结果，并将其存入var4局部变量中
            mv.visitVarInsn(LLOAD, 4); // 读取局部变量var4的值，并将其值压到操作数栈中
            mv.visitInsn(L2I); // 将栈顶var的值由long强转为int类型（int）var4
            mv.visitLdcInsn(autoClassVisitor.getMaxTimeMonitor()); // 将获取到的值压栈
//            mv.visitLdcInsn(new Long(autoClassVisitor.getMaxTimeMonitor())); // 将获取到的值压栈
//            mv.visitInsn(LCMP); // 将var4与刚压栈的10常量做比较运算 var4 == 10L ? 0 : (var4 < 10L ? -1 : 1) 得到的 1,0,-1中的一个值压入栈顶
            Label label1 = new Label();
//            mv.visitJumpInsn(IFLE, label1);//配合LCMP一起使用 当栈顶值<=0 (即对var4和10进行LCMP运算的结果 如果结果 <=0) 则跳至mv.visitLabel(label1);
            mv.visitJumpInsn(IF_ICMPLE, label1); //比较var4>autoClassVisitor.getMaxTimeMonitor()结果小于<= 0 则跳至mv.visitLabel(label1);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); // 弹出System中的out静态成员变量

            mv.visitTypeInsn(NEW, "java/lang/StringBuilder"); // 创建一个StringBuilder对象，并将它压入操作数栈中
            mv.visitInsn(DUP); // 在操作数栈中重复这个值
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false); // 弹出DUP之后的两个副本之一，并调用其构造函数

            mv.visitLdcInsn(autoClassVisitor.getClazzName() + "." + methodName + "(" + methodDesc + ")" + "方法执行耗时-> ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false); // 调用StringBuilder实例的append函数
            mv.visitVarInsn(LLOAD, 4); // 加载var4的值
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("ms");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitLabel(label1);
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
