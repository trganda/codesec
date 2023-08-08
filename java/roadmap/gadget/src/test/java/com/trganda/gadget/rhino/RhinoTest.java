package com.trganda.gadget.rhino;

import org.junit.Test;
import org.mozilla.javascript.ObjToIntMap;

import java.util.Arrays;

public class RhinoTest {
    @Test
    public void test() {
        ObjToIntMap map = new ObjToIntMap(2);
        map.intern("yy");
        map.intern("getOutputProperties");
        map.intern("1getOutputProperties");

        System.out.println(Arrays.toString(map.getKeys()));
    }
}
