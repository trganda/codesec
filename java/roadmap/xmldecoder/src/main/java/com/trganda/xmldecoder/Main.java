package com.trganda.xmldecoder;

import javax.swing.*;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        XMLEncoder e = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream("Test.xml")));
        e.writeObject(new Person("trganda", 18));
        e.close();

    }
}
