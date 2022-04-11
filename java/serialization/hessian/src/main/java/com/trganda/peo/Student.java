package com.trganda.peo;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Student extends People implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Student student = new Student(111, "student", "man");
    private transient String gender;
    private Map<String, Class<Object>> innerMap;
    private List<Student> friends;

    public void setFriends(List<Student> friends) {
        System.out.println("Call Student setFriends");
        this.friends = friends;
    }

    public void getFriends(List<Student> friends) {
        System.out.println("Call Student getFriends");
        this.friends = friends;
    }


    public Map getInnerMap() {
        System.out.println("Call Student getInnerMap");
        return innerMap;
    }

    public void setInnerMap(Map innerMap) {
        System.out.println("Call Student setInnerMap");
        this.innerMap = innerMap;
    }

    public String getGender() {
        System.out.println("Call Student getGender");
        return gender;
    }

    public void setGender(String gender) {
        System.out.println("Call Student setGender");
        this.gender = gender;
    }

    public Student() {
        System.out.println("Call Student default constructor");
    }

    public Student(int id, String name, String gender) {
        System.out.println("Call Student custom constructor");
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    private void readObject(ObjectInputStream ObjectInputStream) {
        System.out.println("Call Student readObject");
    }

    private Object readResolve() {
        System.out.println("Call Student readResolve");

        return student;
    }

    @Override
    public int hashCode() {
        System.out.println("Call Student hashCode");
        return super.hashCode();
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Call Student finalize");

        super.finalize();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", innerMap=" + innerMap +
                ", friends=" + friends +
                '}';
    }
}