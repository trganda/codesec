package com.trganda.loader;

import java.lang.reflect.Method;
import java.util.Base64;

public class ClassLoaderDefine {
    public static void main(String[] args) throws Exception {
        // defineClass 方法为 protected，无法直接访问
        Method defineClass =
                ClassLoader.class.getDeclaredMethod(
                        "defineClass", String.class, byte[].class, int.class, int.class);
        defineClass.setAccessible(true);
        byte[] code = Base64.getDecoder().decode("yv66vgAAADQAIwoACQATCgAUABUIABYKABQAFwcAGAcAGQoABgAaBwAbBwAcAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEACDxjbGluaXQ+AQANU3RhY2tNYXBUYWJsZQcAGAEAClNvdXJjZUZpbGUBAAlFdmlsLmphdmEMAAoACwcAHQwAHgAfAQASb3BlbiAtYSBjYWxjdWxhdG9yDAAgACEBABNqYXZhL2lvL0lPRXhjZXB0aW9uAQAaamF2YS9sYW5nL1J1bnRpbWVFeGNlcHRpb24MAAoAIgEABEV2aWwBABBqYXZhL2xhbmcvT2JqZWN0AQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAGChMamF2YS9sYW5nL1Rocm93YWJsZTspVgAhAAgACQAAAAAAAgABAAoACwABAAwAAAAdAAEAAQAAAAUqtwABsQAAAAEADQAAAAYAAQAAAAMACAAOAAsAAQAMAAAAVAADAAEAAAAXuAACEgO2AARXpwANS7sABlkqtwAHv7EAAQAAAAkADAAFAAIADQAAABYABQAAAAYACQAJAAwABwANAAgAFgAKAA8AAAAHAAJMBwAQCQABABEAAAACABI=");
        Class<?> evil =
                (Class<?>)
                        defineClass.invoke(
                                ClassLoader.getSystemClassLoader(), "Evil", code, 0, code.length);
        evil.newInstance();
    }
}
