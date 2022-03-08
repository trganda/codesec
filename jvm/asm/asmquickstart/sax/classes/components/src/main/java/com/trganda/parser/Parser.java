package com.trganda.parser;

import org.objectweb.asm.ClassReader;

import static org.objectweb.asm.Opcodes.ASM4;

public class Parser {
    public static void main(String[] args) {
        try {
            ClassReader classReader = new ClassReader("java.lang.Integer");
            classReader.accept(new ClassPrinter(ASM4), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
