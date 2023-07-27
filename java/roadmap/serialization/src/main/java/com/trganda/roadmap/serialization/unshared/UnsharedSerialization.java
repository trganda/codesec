package com.trganda.roadmap.serialization.unshared;

import com.trganda.roadmap.serialization.CustomSerialization;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UnsharedSerialization {
    public static void main(String[] args) {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/person.bin")))) {
            CustomSerialization obj1 = new CustomSerialization("trganda", 18);
            oos.writeObject(obj1);
            oos.writeObject(obj1);

            CustomSerialization obj2 = new CustomSerialization("trganda", 19);
            oos.writeObject(obj2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois =
                new ObjectInputStream(Files.newInputStream(Paths.get("target/person.bin")))) {
            CustomSerialization obj1 = (CustomSerialization) ois.readUnshared();
            System.out.println(obj1);
            obj1.age = 1;
            try {
                CustomSerialization obj1ref = (CustomSerialization) ois.readObject();
                System.out.println(obj1ref);
            } catch (InvalidObjectException e) {
                e.printStackTrace();
            }
            CustomSerialization obj2 = (CustomSerialization) ois.readObject();
            System.out.println(obj2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
