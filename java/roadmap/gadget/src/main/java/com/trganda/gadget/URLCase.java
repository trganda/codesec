package com.trganda.gadget;

import java.net.MalformedURLException;
import java.net.URL;

public class URLCase {

    public static void main(String[] args) throws MalformedURLException {
        URL url1 = new URL("http://bmnghxybvd.dnstunnel.run");

        URL url2 = new URL("http://bmnghxybva.dnstunnel.run");

        System.out.println(url1.equals(url2));

        url1.hashCode();
    }

}
