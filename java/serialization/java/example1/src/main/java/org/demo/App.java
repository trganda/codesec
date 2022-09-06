package org.demo;


import java.io.*;

public class App {
    public static void serialize_test(){
        Eval eval = new Eval();
        eval.command = "calc";
        try {
            FileOutputStream fos = new FileOutputStream("user.ser");
            ObjectOutputStream obs = new ObjectOutputStream(fos);
            obs.writeObject(eval);
            obs.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void deserialize_test() {
        try {
            FileInputStream fis = new FileInputStream("user.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        serialize_test();
        deserialize_test();
    }
}
