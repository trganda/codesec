package org.example;

import java.lang.System;

public class HelloImplement implements HelloInterface {
    @Override
    public void doPrint(String str) {
        System.out.println(str);
    }
}
