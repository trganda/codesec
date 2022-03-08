package com.trganda.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;


import java.io.FileInputStream;
import java.io.IOException;

public class Transformer {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("generator.class");

        byte[] bin = new byte[fileInputStream.available()];
        fileInputStream.read(bin);

        ClassReader classReader = new ClassReader(bin);
        ClassWriter classWriter = new ClassWriter(0);

        ChangVersionAdapter adapter = new ChangVersionAdapter(classWriter);
        classReader.accept(adapter, 0);
    }
}
