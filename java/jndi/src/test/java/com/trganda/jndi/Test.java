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
        //properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        //properties.setProperty(Context.PROVIDER_URL, "ldap://192.168.1.3:1389");

        Context context = new InitialContext(properties);
        Object object = context.lookup("ldap://127.0.0.1:1389/tomcat_xxe");
        System.out.println(object);
    }
}
