package com.trganda.roadmap.reflection;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReflectTest {

  @Test
  public void reflection() throws ClassNotFoundException {
    Class.forName(
        "com.trganda.roadmap.reflection.Initialization", true, ReflectTest.class.getClassLoader());
    // new Initialization();
  }

  @Test
  public void touchFile() throws ClassNotFoundException {
    // initialize com.trganda.roadmap.reflection.TouchFile and create /tmp/success file
    Class.forName("com.trganda.roadmap.reflection.TouchFile");
  }

  @Test
  public void getInstance()
      throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName("java.lang.Runtime");

    // This will lead exception "java.lang.IllegalAccessException: Classs
    // com.trganda.roadmap.reflection.ReflectTest can not access a member of class
    // java.lang.Runtime with modifiers "private""
    Runtime rt = (Runtime) clazz.newInstance();
  }

  @Test
  public void getInstance2()
      throws NoSuchMethodException,
          ClassNotFoundException,
          InvocationTargetException,
          IllegalAccessException {
    Class<?> clazz = Class.forName("java.lang.Runtime");
    clazz
        .getMethod("exec", String.class)
        .invoke(clazz.getMethod("getRuntime").invoke(clazz), "open -a calculator");
  }

  @Test
  public void getInstance3()
      throws NoSuchMethodException,
          ClassNotFoundException,
          InvocationTargetException,
          InstantiationException,
          IllegalAccessException,
          IOException {
    Class<?> clazz = Class.forName("java.lang.ProcessBuilder");
    ((ProcessBuilder)
            clazz.getConstructor(List.class).newInstance(Arrays.asList("open -a calculator")))
        .start();
  }

  @Test
  public void getMethod()
      throws ClassNotFoundException,
          NoSuchMethodException,
          InvocationTargetException,
          InstantiationException,
          IllegalAccessException {
    Class<?> clazz = Class.forName("java.lang.ProcessBuilder");
    // with type cast
    clazz
        .getMethod("start")
        .invoke(
            clazz
                .getConstructor(List.class)
                .newInstance(Collections.singletonList("open -a calculator")));
  }

  @Test
  public void getDeclaredMethod()
      throws NoSuchMethodException,
          ClassNotFoundException,
          InvocationTargetException,
          InstantiationException,
          IllegalAccessException {
    Class<?> clazz = Class.forName("java.lang.Runtime");
    Constructor<?> m = clazz.getDeclaredConstructor();
    m.setAccessible(true);
    clazz.getMethod("exec", String.class).invoke(m.newInstance(), "open -a calculator");
  }
}
