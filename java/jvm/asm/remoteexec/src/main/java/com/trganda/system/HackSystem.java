package com.trganda.system;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class HackSystem {

    public static final InputStream in = System.in;

    private static ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public final static PrintStream out = new PrintStream(buffer);
    public final static PrintStream err = out;

    public static String getBufferString() {
        return buffer.toString();
    }

    public static void clearBuffer() {
        buffer.reset();
    }

    public static void setSecurityManager(final SecurityManager s) {
        System.setSecurityManager(s);
    }

    public static SecurityManager getSecurityManager() {
        return System.getSecurityManager();
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static void arraycopy(Object src, int srcPos, Object dst, int dstPos, int length) {
        System.arraycopy(src, srcPos, dst, dstPos, length);
    }

    public static int identifyHashCode(Object x) {
        return System.identityHashCode(x);
    }
}
