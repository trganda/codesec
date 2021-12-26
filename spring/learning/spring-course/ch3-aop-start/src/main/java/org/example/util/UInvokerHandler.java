package org.example.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UInvokerHandler implements InvocationHandler {
    private Object obj;

    public UInvokerHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(obj, args);
        System.out.println("call with proxy.");
        return null;
    }
}
