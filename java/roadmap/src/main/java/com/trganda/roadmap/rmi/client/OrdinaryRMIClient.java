package com.trganda.roadmap.rmi.client;

import com.trganda.roadmap.rmi.remote.RemoteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrdinaryRMIClient {

    public static void main(String[] args) {
        try {
            String addr = (args[0]);
            int port = Integer.parseInt(args[1]);
            String name = args[2];
            String params = args[3];

            System.out.println(addr + " " + port + " " + name + " " + params);

            // default host: localhost; port: 1099
            Registry registry = LocateRegistry.getRegistry(addr, port);
            RemoteService rs = (RemoteService) registry.lookup(name);

            rs.doSome(params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
