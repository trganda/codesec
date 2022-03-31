package com.trganda.modifier;

import org.junit.Test;

import java.io.*;

public class ClassModifierTest {

    @Test
    public void modifyUTF8ConstantTest() throws IOException {
        InputStream is = new FileInputStream(this.getClass().getResource("/").getPath().toString() + File.separator + "App.class");
        byte[] b = new byte[is.available()];
        is.read(b);
        is.close();

        ClassModifier classModifier = new ClassModifier(b);
        byte[] nb = classModifier.modifyUTF8Constant("java/lang/System", "com/trganda/system/HackSystem");
    }
}
