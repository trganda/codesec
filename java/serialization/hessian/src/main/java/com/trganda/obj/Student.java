package com.trganda.obj;
import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private transient String gender;

    public int getId() {
        System.out.println("Student getId call");
        return id;
    }

    public void setId(int id) {
        System.out.println("Student setId call");
        this.id = id;
    }

    public String getName() {
        System.out.println("Student getName call");
        return name;
    }

    public void setName(String name) {
        System.out.println("Student setName call");
        this.name = name;
    }

    public String getGender() {
        System.out.println("Student getGender call");
        return gender;
    }

    public void setGender(String gender) {
        System.out.println("Student setGender call");
        this.gender = gender;
    }

    public Student() {
        System.out.println("Student default constractor call");
    }

    public Student(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student(id=" + id + ",name=" + name + ",gender=" + gender + ")";
    }
}
