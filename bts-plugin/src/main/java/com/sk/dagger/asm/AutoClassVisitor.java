package com.sk.dagger.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;


public class AutoClassVisitor extends ClassVisitor {
    public AutoClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new TimeMethodVisitor(api, methodVisitor, access, name, descriptor);
    }
}
