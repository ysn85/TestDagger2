package com.sk.dagger.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;


public class AutoClassVisitor extends ClassVisitor {

    private boolean needInject;
    private String clazzName;


    public AutoClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        TimeMethodVisitor timeMethodVisitor = new TimeMethodVisitor(api, methodVisitor, access, name, descriptor, this);
        return timeMethodVisitor;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        clazzName = name;
    }

    public boolean isNeedInject() {
        return needInject;
    }

    public void setNeedInject(boolean needInject) {
        this.needInject = needInject;
    }

    public String getClazzName() {
        return clazzName;
    }
}
