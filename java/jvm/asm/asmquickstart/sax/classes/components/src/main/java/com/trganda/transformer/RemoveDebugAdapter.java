package com.trganda.transformer;

import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ASM4;

public class RemoveDebugAdapter extends ClassVisitor {

    public RemoveDebugAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public void visitSource(String source, String debug) {
        // Do not delegate the debug info
        // for removing.
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }

    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
    }
}
