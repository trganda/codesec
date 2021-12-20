package org.demo;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class App {
    public static void serialize_test(){
        User user = new User();
        user.name = "jong";
        user.address = "guess";
        user.number = 6;
        user.info();
        try {
            FileOutputStream fos = new FileOutputStream("user.ser");
            ObjectOutputStream obs = new ObjectOutputStream(fos);
            obs.writeObject(user);
            obs.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        serialize_test();
    }
}
