package com.trganda.xmldecoder;

import com.trganda.xmldecoder.bean.Person;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XMLEncoderTest {
    public static void main(String[] args) throws Exception {
        XMLEncoder e = new XMLEncoder(new FileOutputStream("target/person.xml"));
        e.writeObject(new Person("trganda", 18));
        e.close();

        XMLDecoder d = new XMLDecoder(new FileInputStream("target/person.xml"));
        Person person = (Person) d.readObject();
        d.close();

        System.out.println(person);
    }
}
