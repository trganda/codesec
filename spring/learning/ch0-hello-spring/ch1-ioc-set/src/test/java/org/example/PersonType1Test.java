package org.example;

import org.example.type1.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class PersonType1Test {
    @Test
    public void testCase01() {
        String config = "type1/config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        Person p = (Person) ctx.getBean("person");
        System.out.println(p);
    }

    @Test
    public void testCase02() {
        String config = "type1/config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        File file = (File) ctx.getBean("ifile");
        System.out.println(file.getName());
    }
}
