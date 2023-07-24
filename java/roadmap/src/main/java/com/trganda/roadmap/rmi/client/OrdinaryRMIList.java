package com.trganda.roadmap.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrdinaryRMIList {
    public static void main(String[] args) {
        try {
            int registryPort = Integer.parseInt(args[0]);
            String registryIP = args[1];

            System.out.println(registryPort + " " + registryIP);

            Registry reg = LocateRegistry.getRegistry(registryIP, registryPort);
            String[] list = reg.list();

            for (String s : list) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
