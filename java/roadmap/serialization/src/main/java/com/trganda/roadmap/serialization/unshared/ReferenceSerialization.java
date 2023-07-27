package com.trganda.roadmap.serialization.unshared;

import com.trganda.roadmap.serialization.CustomSerialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReferenceSerialization {
    public static void main(String[] args) {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/person.bin")))) {
            CustomSerialization obj1 = new CustomSerialization("trganda", 18);
            oos.writeObject(obj1);
            oos.writeObject(obj1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois =
                new ObjectInputStream(Files.newInputStream(Paths.get("target/person.bin")))) {
            CustomSerialization obj1 = (CustomSerialization) ois.readObject();
            System.out.println(obj1);
            obj1.age = 1; // [4] 对第一次读取的对象的成员属性进行修改
            CustomSerialization obj1ref = (CustomSerialization) ois.readObject();
            System.out.println(obj1ref);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
