package com.trganda.roadmap.serialization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FieldSerialization implements Serializable {
    public String name;
    public transient int age;
    public static String bio = "This is a bio";
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("bio", String.class)
    };

    public FieldSerialization(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/person.bin")))) {
            FieldSerialization fieldSerialization = new FieldSerialization("trganda", 25);
            FieldSerialization.bio = "This is a new bio";
            oos.writeObject(fieldSerialization);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
