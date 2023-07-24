package com.trganda.roadmap.rmi.client;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrdinaryRMIDumper {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            String addr = (args[1]);
            String name = args[2];

            System.out.println(addr + " " + port + " " + name);

            // default host: localhost; port: 1099
            Registry registry = LocateRegistry.getRegistry(addr, port);
            Remote stub = registry.lookup(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
