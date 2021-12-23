package com.sk.dagger.asm;

import com.sk.dagger.bts_plugin.HelloExtension;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class AutoClassVisitor extends ClassVisitor {

    private boolean needInject;
    private String clazzName;
    private HelloExtension helloExtension;


    public AutoClassVisitor(int api, ClassVisitor classVisitor, HelloExtension helloExtension) {
        super(api, classVisitor);
        this.helloExtension = helloExtension;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ((access & Opcodes.ACC_INTERFACE) == 0 && "<init>" != name && "<clinit>" != name) {
            methodVisitor = new TimeMethodVisitor(api, methodVisitor, access, name, descriptor, this);
        }
        return methodVisitor;
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

    public int getMaxTimeMonitor() {
        if (helloExtension == null) {
            return 0;
        }
        return helloExtension.getMaxTimeMonitor();
    }
}
