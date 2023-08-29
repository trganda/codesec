package com.trganda.xstream;

import com.thoughtworks.xstream.XStream;

public class URLDNS {
    public static void main(String[] args) {
        XStream xStream = new XStream();

        String xml = "<map>\n" +
            "  <entry>\n" +
            "    <url>http://dwzpxhothu.dgrh3.cn</url>\n" +
            "    <string>foo</string>\n" +
            "  </entry>\n" +
            "</map>";

        Object object = xStream.fromXML(xml);
        System.out.println(object);
    }
}
