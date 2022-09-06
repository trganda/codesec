package org.demo;


import java.io.IOException;
import java.io.Serializable;

public class Eval implements Serializable {
    public String command;

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        Runtime.getRuntime().exec(command);
    }

}