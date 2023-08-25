package com.trganda.xmldecoder;

import java.beans.XMLDecoder;
import java.io.FileInputStream;

public class EvilXMLDecoder {
    public static void main(String[] args) throws Exception {
        XMLDecoder d = new XMLDecoder(new FileInputStream("target/classes/processbuilder.xml"));
        Object person = d.readObject();
        d.close();
    }
}
