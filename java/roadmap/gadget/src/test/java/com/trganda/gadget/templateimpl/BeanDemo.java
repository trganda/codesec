package com.trganda.gadget.templateimpl;

import java.util.Properties;

public class BeanDemo {
    private int _id;

    private Properties _outputProperties;

    public int get_id() {
        System.out.println("get_id");
        return _id;
    }

    public Properties get_outputProperties() {
        System.out.println("get_outputProperties");
        return _outputProperties;
    }
}
