package com.trganda.transformer;

import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.V1_5;

public class ChangVersionAdapter extends ClassVisitor {

    public ChangVersionAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // modifier the version to V1_5
        super.visit(V1_5, access, name, signature, superName, interfaces);
    }
}
