package com.trganda.roadmap.reflection;

public class Initialization {
    // Execute while class was instanced, early then constructor
    {
        System.out.println("InitializeTest Block");
    }

    // Execute while class is initialized
    static {
        System.out.println("InitializeTest Static Block");
    }

    // Execute while class was instanced
    public Initialization() {
        System.out.println("InitializeTest Constructor");
    }
}
