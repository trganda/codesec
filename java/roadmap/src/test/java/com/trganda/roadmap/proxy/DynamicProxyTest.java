package com.trganda.roadmap.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DynamicProxyTest {
  @Test
  public void givenDynamicProxy_thenPutWorks() {
    Map proxyInstance =
        (Map)
            Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[] {Map.class},
                new DynamicInvocationHandler());

    proxyInstance.put("hello", "world");
  }

  @Test
  public void givenTimingProxy() {
    Map mapProxyInstance =
        (Map)
            Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[] {Map.class},
                new TimingDynamicInvocationHandler(new HashMap<>()));

    mapProxyInstance.put("hello", "world");

    CharSequence csProxyInstance =
        (CharSequence)
            Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[] {CharSequence.class},
                new TimingDynamicInvocationHandler("Hello World"));

    csProxyInstance.length();
  }
}
