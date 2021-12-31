package org.trganda;

import java.beans.XMLDecoder;
import java.io.FileInputStream;

public class App 
{
    public static void main( String[] args )
    {
        String filename = "poc.xml";

        try {
            XMLDecoder XD = new XMLDecoder(new FileInputStream(filename));
            Object o = XD.readObject();
            System.out.println(o);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
