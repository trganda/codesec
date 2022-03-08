package com.trganda.transformer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.Objects;

import static org.objectweb.asm.Opcodes.ASM4;

public class RemoveMethodAdapter extends ClassVisitor {

    public RemoveMethodAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        // Remove the method with name "compareTo"
        if (Objects.equals(name, "compareTo")) {
            return null;
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
