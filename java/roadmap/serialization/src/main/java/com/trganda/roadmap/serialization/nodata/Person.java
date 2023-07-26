package com.trganda.roadmap.serialization.nodata;

import java.io.ObjectStreamException;
import java.io.Serializable;

class Person implements Serializable {
    protected String name;
    protected int age;

    Person() {
        this("John", 1);
    }

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void readObjectNoData() throws ObjectStreamException {
        name = "John";
        age = 1;
        throw new IllegalArgumentException();
    }
}
