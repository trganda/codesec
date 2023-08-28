package com.trganda.xstream.eventhandler;

import java.beans.EventHandler;
import java.util.Set;
import java.util.TreeSet;

public class EventHandlerExec {
    public static void main(String[] args) {
        Set<Comparable> set = new TreeSet<>();
        set.add("foo");
        set.add(EventHandler.create(Comparable.class, new
                ProcessBuilder("open", "-a", "calculator"), "start"));
    }
}
