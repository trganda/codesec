package com.trganda.xstream.serializable;

import com.thoughtworks.xstream.XStream;
import com.trganda.gadget.lazymap.LazyMapAnnotationExec;

public class XStreamLazyMapAnnotationExec {

    public static Object getObject() throws Exception {
        return LazyMapAnnotationExec.getObject();
    }

    public static void main(String[] args) throws Exception {
        XStream xStream = new XStream();
        xStream.fromXML(xStream.toXML(getObject()));
    }
}
