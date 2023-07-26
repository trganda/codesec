package com.trganda.roadmap.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DifferUIDSerialization {
    public static void main(String[] args) {
        try (ObjectInputStream ois =
                new ObjectInputStream(Files.newInputStream(Paths.get("target/person.bin")))) {
            VersionUIDSerialization person = (VersionUIDSerialization) ois.readObject();
            System.out.println(person);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
