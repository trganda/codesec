package org.example.type0;

import org.example.Tool;

public class Person {
    private String name;
    private int age;
    // Reference Type
    private Tool tool;

    public void setName(String name) {
        System.out.println("call setName");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        System.out.println("call setAge");
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", tool=" + tool +
                '}';
    }
}
