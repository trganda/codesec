package org.demo;


import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public transient String address;
    public int number;

    public void info() {
        System.out.println("Name: " + name + "\nAddress: " + address + "\nNumber: " + number);
    }

}