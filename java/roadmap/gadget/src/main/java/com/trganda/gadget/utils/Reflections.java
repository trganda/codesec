package com.trganda.gadget.utils;

import java.lang.reflect.*;
import java.util.ArrayList;

import sun.reflect.ReflectionFactory;

import com.nqzero.permit.Permit;

@SuppressWarnings ( "restriction" )
public class Reflections {

    public static void setAccessible(AccessibleObject member) {
        String versionStr = System.getProperty("java.version");
        int javaVersion = Integer.parseInt(versionStr.split("\\.")[0]);
        if (javaVersion < 12) {
            // quiet runtime warnings from JDK9+
            Permit.setAccessible(member);
        } else {
            // not possible to quiet runtime warnings anymore...
            // see https://bugs.openjdk.java.net/browse/JDK-8210522
            // to understand impact on Permit (i.e. it does not work
            // anymore with Java >= 12)
            member.setAccessible(true);
        }
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        }
        catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

    public static Object newInstance(String className, Object ... args) throws Exception {
        return getFirstCtor(className).newInstance(args);
    }

    public static Object newInstance(String className) throws Exception {
        Constructor<?> constructor = getFirstCtor(className);

        Class[] argsType = constructor.getParameterTypes();
        ArrayList<Object> args = new ArrayList<>();

        for (Class clazz: argsType) {
            if (clazz.isPrimitive()) {
                args.add(createDefaultPrimitive(clazz));
            } else if (clazz.isArray()) {
                Class elementClazz = clazz.getComponentType();
                args.add(Array.newInstance(elementClazz, 0));
            } else {
                Object arg = newInstance(className);
                args.add(arg);
            }
        }

        return constructor.newInstance(args.toArray());
    }

    public static Object createDefaultPrimitive(Class clazz) {
        if (clazz == boolean.class) {
            return false;
        } else if (clazz == char.class) {
            return '0';
        } else if (clazz == byte.class) {
            return (byte)64;
        } else if (clazz == short.class || clazz == int.class ||
            clazz == long.class || clazz == float.class || clazz == double.class) {
            return 0;
        } else {
            return null;
        }
    }

    public static <T> T createWithoutConstructor ( Class<T> classToInstantiate )
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }

    @SuppressWarnings ( {"unchecked"} )
    public static <T> T createWithConstructor ( Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs )
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        setAccessible(objCons);
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        setAccessible(sc);
        return (T)sc.newInstance(consArgs);
    }

}
