package com.trganda.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class JNDIRMIReference {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");

        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        properties.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099");

        try {
            Context context = new InitialContext(properties);
            context.lookup("foo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
