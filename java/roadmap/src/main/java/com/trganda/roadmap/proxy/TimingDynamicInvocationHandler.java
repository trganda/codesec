package com.trganda.roadmap.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TimingDynamicInvocationHandler implements InvocationHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(TimingDynamicInvocationHandler.class);

  private final Map<String, Method> methods = new HashMap<>();

  private final Object target;

  public TimingDynamicInvocationHandler(Object target) {
    this.target = target;

    // get all methods of target
    for (Method method : target.getClass().getDeclaredMethods()) {
      this.methods.put(method.getName(), method);
    }
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    long start = System.nanoTime();

    // invoke the method and get the result
    Object result = methods.get(method.getName()).invoke(target, args);
    long elapsed = System.nanoTime() - start;

    LOGGER.info("Executing {} finished in {} ns", method.getName(), elapsed);

    return result;
  }
}
