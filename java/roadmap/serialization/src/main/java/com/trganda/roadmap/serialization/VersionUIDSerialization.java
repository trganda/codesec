package com.trganda.roadmap.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VersionUIDSerialization implements java.io.Serializable {
    public String name;
    public transient int age;
    public static String bio = "This is a bio";
    private static final long serialVersionUID = 1111111111111111112L;

    public VersionUIDSerialization(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(age);
        s.writeObject("This is a object");
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        age = s.readInt();
        String message = (String) s.readObject();
        System.out.println(message);
    }

    @Override
    public String toString() {
        return "CustomSerialization2{"
                + "name='"
                + name
                + '\''
                + ", age="
                + age
                + '\''
                + ", bio='"
                + bio
                + '\''
                + '}';
    }

    public static void main(String[] args) {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/person.bin")))) {
            VersionUIDSerialization customSerialization2 =
                    new VersionUIDSerialization("Trganda", 25);
            VersionUIDSerialization.bio = "This is a new bio";
            oos.writeObject(customSerialization2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
