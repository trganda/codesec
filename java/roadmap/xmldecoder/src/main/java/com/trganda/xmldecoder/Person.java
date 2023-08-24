package com.trganda.xmldecoder;

public class Person {
    public String name;

    private int age;

    public Person() {
        this.name = "t";
        this.age = 1;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        System.out.println("call getName of Person");
        return name;
    }

    public void setName(String name) {
        System.out.println("call setName of Person");
        this.name = name;
    }

    public int getAge() {
        System.out.println("call getAge of Person");
        return age;
    }

    public void setAge(int age) {
        System.out.println("call setAge of Person");
        this.age = age;
    }
}
