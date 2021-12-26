package org.example.type0;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Component: Create an object of Person, assign property with key 'value'
 */
@Component(value = "person")
public class Person {
    /**
     * assign with value by reflect (no setter call)
     */
    @Value("trganda")
    private String name;
    @Value("18")
    private int age;

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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}