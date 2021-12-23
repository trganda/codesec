package org.example;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloImplementTest {
    @Test
    public void testCase01() {
        HelloImplement hl = new HelloImplement();
        hl.doPrint("hi");
    }

    @Test
    public void testCase02() {
        String config = "config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        HelloInterface hl = (HelloInterface) ctx.getBean("helloimp");
        hl.doPrint("hi");
    }
}
