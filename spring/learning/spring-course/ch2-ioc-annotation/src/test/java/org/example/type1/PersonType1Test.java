package org.example.type1;

import org.example.type1.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersonType1Test {
    @Test
    public void testCase01() {
        String config = "type1/config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        Person p = (Person) ctx.getBean("person");
        System.out.println(p);
    }
}
