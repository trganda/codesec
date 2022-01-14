package org.trganda.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class CustomClassWriter {
    static String className = "java.lang.Integer";
    static String cloneableInterface = "java/lang/Cloneable";
    ClassReader reader;
    ClassWriter writer;

    AddFieldAdapter addFieldAdapter;

    public CustomClassWriter() throws IOException {
        reader = new ClassReader(className);
        writer = new ClassWriter(reader, 0);
    }

    public byte[] addField() {
        addFieldAdapter = new AddFieldAdapter("aNewBoolenField", Opcodes.ACC_PUBLIC, writer);
        reader.accept(addFieldAdapter, 0);
        return writer.toByteArray();
    }
}
