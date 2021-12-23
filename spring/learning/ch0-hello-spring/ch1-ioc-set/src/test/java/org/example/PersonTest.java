package org.example;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersonTest {
    @Test
    public void testCase01() {
        String config = "config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        Person p = (Person) ctx.getBean("person");
        System.out.println(p);
    }

    @Test
    public void testCase02() {
        String config = "config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        Person p = (Person) ctx.getBean("person");
        System.out.println(p);
    }
}
