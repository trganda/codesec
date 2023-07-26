package com.trganda.roadmap.serialization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LifeSerialization implements Serializable {
    public String name;
    public int age;

    public LifeSerialization(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        System.out.println("call writeObject");
    }

    private void readObject(java.io.ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        System.out.println("call readObject");
    }

    private Object writeReplace() throws ObjectStreamException {
        System.out.println("call writeReplace");
        return new LifeSerialization("writeReplace", 18);
    }

    private Object readResolve() throws ObjectStreamException {
        System.out.println("call readResolve");
        return new LifeSerialization("readResolve", 18);
    }

    @Override
    public String toString() {
        return "LifeSerialization{" + "name='" + name + '\'' + ", age=" + age + '}';
    }

    public static void main(String[] args) {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/person.bin")))) {
            oos.writeObject(new LifeSerialization("trganda", 18));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois =
                new ObjectInputStream(Files.newInputStream(Paths.get("target/person.bin")))) {
            LifeSerialization life = (LifeSerialization) ois.readObject();
            System.out.println(life);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
