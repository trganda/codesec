package org.example;

import org.example.impl.SomeServiceImpl;
import org.example.service.SomeService;
import org.example.util.UInvokerHandler;

import java.lang.reflect.Proxy;

public class UApp {
    // Dynamic proxy demo
    public static void main(String[] args) {
        SomeServiceImpl someServiceImpl = new SomeServiceImpl();

        UInvokerHandler invokerHandler = new UInvokerHandler(someServiceImpl);
        // Create a proxy
        SomeService proxy = (SomeService) Proxy.newProxyInstance(someServiceImpl.getClass().getClassLoader(),
                someServiceImpl.getClass().getInterfaces(),
                invokerHandler);

        proxy.doSome();
    }
}
