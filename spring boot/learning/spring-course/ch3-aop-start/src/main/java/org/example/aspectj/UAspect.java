package org.example.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component(value = "uaspect")
@Aspect
public class UAspect {

    /**
     * @Before annotation
     * property:
     *       - value: aspect expression, execution(access-privilege return-type package-name.class-name.function-name(argument-list))
     */
    @Before(value = "execution(public void org.example.impl.SomeServiceImpl.doSome(String))")
    public void runBefore() {
        System.out.println("Run before you at: " + new Date());
    }
}
