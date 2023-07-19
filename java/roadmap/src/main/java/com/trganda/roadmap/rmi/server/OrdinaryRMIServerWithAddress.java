package com.trganda.roadmap.rmi.server;

import com.trganda.roadmap.rmi.impl.RemoteServiceImpl;
import com.trganda.roadmap.rmi.impl.ServerSocketFactoryImpl;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrdinaryRMIServerWithAddress {

    public static void main(String[] args) {
        try {
            int registryPort = Integer.parseInt(args[0]);
            String registryIP = args[1];
            int serverPort = Integer.parseInt(args[2]);
            String serverIP = args[3];
            String serverName = args[4];

            System.out.println(registryPort + " " + serverPort + " " + serverIP + " " + serverName);

            // explicitly set the port and address of server
            RemoteServiceImpl rsi =
                    new RemoteServiceImpl(
                            serverPort,
                            null,
                            new ServerSocketFactoryImpl(InetAddress.getByName(serverIP)));
            Registry reg =
                    LocateRegistry.createRegistry(
                            registryPort,
                            null,
                            new ServerSocketFactoryImpl(InetAddress.getByName(registryIP)));

            reg.bind(serverName, rsi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
