package com.trganda.gadget.hash;

import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

public class HashCollisionTest {
    @Test
    public void lazyMapHashTest() {
        ConstantTransformer fake = new ConstantTransformer("fake");

        LazyMap lazyMap = (LazyMap) LazyMap.decorate(new HashMap(), fake);
        lazyMap.put("yy", 1);

        LazyMap lazyMap2 = (LazyMap) LazyMap.decorate(new HashMap<>(), fake);
        lazyMap2.put("zZ", 1);

        System.out.println(Objects.hash("yy") + " " + Objects.hash("yy"));

        System.out.println("Before put Hashtable " + lazyMap.hashCode());
        System.out.println("Before put Hashtable " + lazyMap2.hashCode());

        Hashtable hashtable = new Hashtable<>();
        hashtable.put(lazyMap, 1);
        hashtable.put(lazyMap2, 2);

        System.out.println("After put Hashtable " + lazyMap2.hashCode());
        lazyMap2.remove("yy");
        System.out.println("After remove yy " + lazyMap2.hashCode());
    }
}
