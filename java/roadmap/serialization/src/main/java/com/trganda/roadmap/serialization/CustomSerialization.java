package com.trganda.roadmap.serialization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomSerialization implements java.io.Serializable {
    public String name;
    transient public int age;

    public CustomSerialization(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject("This is a object");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        String message = (String) s.readObject();
        System.out.println(message);
    }

    public static void main(String[] args) {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/person.bin")))) {
            oos.writeObject(new CustomSerialization("trganda", 18));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
