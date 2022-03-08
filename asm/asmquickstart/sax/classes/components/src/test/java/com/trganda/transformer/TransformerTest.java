package com.trganda.transformer;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TransformerTest {

    @Test
    public void testRemoveDebugAdapter() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("generator.class");

        byte[] bin = new byte[fileInputStream.available()];
        fileInputStream.read(bin);
        fileInputStream.close();

        ClassReader classReader = new ClassReader(bin);
        ClassWriter classWriter = new ClassWriter(0);

        RemoveDebugAdapter removeDebugAdapter = new RemoveDebugAdapter(classWriter);
        classReader.accept(removeDebugAdapter, 0);

        byte[] bout = classWriter.toByteArray();

        FileOutputStream fileOutputStream = new FileOutputStream("transformed.class");
        fileOutputStream.write(bout);

        fileOutputStream.close();
    }

    @Test
    public void testRemoveMethodAdapter() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("generator.class");

        byte[] bin = new byte[fileInputStream.available()];
        fileInputStream.read(bin);
        fileInputStream.close();

        ClassReader classReader = new ClassReader(bin);
        ClassWriter classWriter = new ClassWriter(0);

        RemoveMethodAdapter removeMethodAdapter = new RemoveMethodAdapter(classWriter);
        classReader.accept(removeMethodAdapter, 0);

        byte[] bout = classWriter.toByteArray();

        FileOutputStream fileOutputStream = new FileOutputStream("transformed.class");
        fileOutputStream.write(bout);

        fileOutputStream.close();
    }
}
