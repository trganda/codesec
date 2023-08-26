package com.trganda.xmldecoder;

import java.beans.XMLDecoder;
import java.io.FileInputStream;

public class URLDNS {
    public static void main(String[] args) throws Exception {
        XMLDecoder d = new XMLDecoder(new FileInputStream("target/classes/urldns.xml"));
        Object person = d.readObject();
        d.close();
    }
}
