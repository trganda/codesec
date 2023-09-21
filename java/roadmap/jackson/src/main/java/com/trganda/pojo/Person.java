package com.trganda.pojo;

public class Person {
    public String name;
    public int age;
    public PhoneNumber phone; // embedded POJO

    private Person() {

    }

    public Person(String name, int age, PhoneNumber phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public void setAge(int age) {
        System.out.println("call setAge");
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", phone=" + phone +
            '}';
    }
}
