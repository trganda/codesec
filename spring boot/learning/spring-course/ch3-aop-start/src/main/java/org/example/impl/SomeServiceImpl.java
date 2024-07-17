package org.example.impl;

import org.example.service.SomeService;
import org.springframework.stereotype.Component;

@Component(value = "someimpl")
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("call doSome.");
    }

    @Override
    public void doSome(String doName) {
        System.out.println("call doSome(" + doName + ").");
    }
}
