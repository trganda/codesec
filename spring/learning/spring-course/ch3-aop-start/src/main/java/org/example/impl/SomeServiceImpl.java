package org.example.impl;

import org.example.service.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("call doSome.");
    }
}
