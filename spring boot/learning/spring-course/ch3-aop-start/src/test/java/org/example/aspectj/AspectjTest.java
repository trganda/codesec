package org.example.aspectj;

import org.example.service.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspectjTest {
    @Test
    public void runBeforeTest() {
        String config = "config.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        SomeService proxy = (SomeService) ctx.getBean("someimpl");
        // aspect run
        proxy.doSome("1");
        // looks like normal proxy
        proxy.doSome();
    }
}
