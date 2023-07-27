package com.trganda.roadmap.serialization.nodata;

import java.io.*;

//public class Employee implements Serializable {  // v1
//    public String name;
//    public int age;
//    protected String address;
//    static final long serialVersionUID = 1L;
//
//    public Employee() {
//        name = "John";
//        age = 1;
//        address = "N/A";
//    }
//
//    public Employee(String name, int age, String address) {
//        this.name = name;
//        this.age = age;
//        this.address = address;
//    }
//
//    public static void main(String[] args) {
//        Employee e = new Employee();
//
//        try {
//            FileOutputStream fileOut = new FileOutputStream("target/employee.bin");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(e);
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized data is saved in tmp.txt");
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }
//}

public class Employee extends Person { // v2
    protected String address;
    static final long serialVersionUID = 1L;

    public Employee() {
        super();
        address = "N/A";
    }

    public Employee(String name, int age, String address) {
        super(name, age);
        this.address = address;
    }

    public static void main(String[] args) {
        Employee e = null;
        try {
            FileInputStream fileIn = new FileInputStream("target/employee.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Employee) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("IOException");
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Deserialized Employee...");
        System.out.println("Name: " + e.name);
        System.out.println("Address: " + e.address);
        System.out.println("Age: " + e.age);
    }
}