package com.trganda.writer;

import org.junit.Test;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class GeneratorTest {
    @Test
    public void generatorTest() throws IOException {
        ClassWriter classWriter = new ClassWriter(0);

        classWriter.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[] { "pkg/Measurable" });
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, -1).visitEnd();
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, 0).visitEnd();
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, 1).visitEnd();
        classWriter.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        classWriter.visitEnd();
        byte[] b = classWriter.toByteArray();

        FileOutputStream fileOutputStream = new FileOutputStream(new File("generator.class"));

        fileOutputStream.write(b);
        fileOutputStream.close();
    }
}
