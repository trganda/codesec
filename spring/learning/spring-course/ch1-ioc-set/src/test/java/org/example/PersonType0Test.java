package org.example;

import org.example.type0.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersonType0Test {
    @Test
    public void testCase01() {
        String config = "type0/config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        Person p = (Person) ctx.getBean("person");
        System.out.println(p);
    }

    @Test
    public void testCase02() {
        String config = "type0/config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        Person p = (Person) ctx.getBean("person");
        System.out.println(p);
    }

    @Test
    public void testCase03() {
        String config = "type1/config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        Person p = (Person) ctx.getBean("person1");
        System.out.println(p);
    }
}
