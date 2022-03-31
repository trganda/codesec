package com.trganda;

import com.trganda.modifier.ClassModifier;
import org.junit.Test;

import java.io.*;

public class JavaClassExecutorTest {

    @Test
    public void executeTest() throws IOException {
        InputStream is = new FileInputStream(this.getClass().getResource("/").getPath().toString() + File.separator + "App.class");
        byte[] b = new byte[is.available()];
        is.read(b);
        is.close();

        ClassModifier classModifier = new ClassModifier(b);
        byte[] nb = classModifier.modifyUTF8Constant("java/lang/System", "com/trganda/system/HackSystem");

        System.out.println(JavaClassExecutor.execute(nb));
    }
}
