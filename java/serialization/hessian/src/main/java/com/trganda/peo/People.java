package com.trganda.peo;

public class People {
    int id;
    String name;

    public int getId() {
        System.out.println("Call People getId");
        return id;
    }

    public void setId(int id) {
        System.out.println("Call People setId");
        this.id = id;
    }

    public String getName() {
        System.out.println("Call People getName");
        return name;
    }

    public void setName(String name) {
        System.out.println("Call People setName");
        this.name = name;
    }
}