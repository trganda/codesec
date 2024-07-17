package org.example.type1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Retention;

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

    /**
     * use byType default, use byName by special the @Qualifier
     * property:
     *       - required=true : there must be a bean can be assigned to.
     *       - required=false : continue without error.
     */
    @Autowired(required = true)
    @Qualifier("tool1")
    private Tool tool;

    /**
     * use byName first, if it's failed, try byType.
     * property:
     *       - name="bean id" : use byName force.
     */
    @Resource(name = "tool")
    private Tool toolCopy;

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
                ", tool=" + tool +
                ", toolCopy=" + toolCopy +
                '}';
    }
}