package com.trganda;

import com.trganda.loader.HotSwapClassLoader;
import com.trganda.modifier.ClassModifier;
import com.trganda.system.HackSystem;

import java.lang.reflect.Method;

public class JavaClassExecutor {

    /**
     * Execute the **main** method of the parameter
     * @param classBytes bytes format of a java class file
     * @return execute result
     */
    public static String execute(byte[] classBytes) {
        HackSystem.clearBuffer();

        ClassModifier classModifier = new ClassModifier(classBytes);
        byte[] modifiedBytes = classModifier.modifyUTF8Constant("java/lang/System",
                "com/trganda/system/HackSystem");

        HotSwapClassLoader classLoader = new HotSwapClassLoader();
        Class clazz = classLoader.loadByte(modifiedBytes);
        try {
            Method method = clazz.getMethod("main", String[].class);
            method.invoke(null, (Object) new String[] {null});
        } catch (Exception e) {
            e.printStackTrace(HackSystem.out);
        }

        return HackSystem.getBufferString();
    }
}
