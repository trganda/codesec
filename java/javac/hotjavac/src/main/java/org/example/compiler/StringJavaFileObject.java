package org.example.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class StringJavaFileObject extends SimpleJavaFileObject {

    private String source;
    private ByteArrayOutputStream byteArrayOutputStream;

    /**
     * Creata a StringJavaFileObject with given source
     * @param name source name
     * @param source source content
     */
    public StringJavaFileObject(String name, String source) {
        super(URI.create("String:///" + name + Kind.SOURCE.extension), Kind.SOURCE);
        this.source = source;
    }

    public StringJavaFileObject(String name, Kind kind) {
        super(URI.create("String:///" + name + Kind.SOURCE.extension), kind);
        this.source = null;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        if (source == null) {
            throw new IllegalArgumentException("source is null.");
        }
        return source;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        return byteArrayOutputStream;
    }

    public byte[] getCompiledBytes() {
        return byteArrayOutputStream.toByteArray();
    }
}
