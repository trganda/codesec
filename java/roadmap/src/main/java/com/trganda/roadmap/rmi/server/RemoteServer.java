package com.trganda.roadmap.rmi.server;

import com.trganda.roadmap.rmi.impl.RemoteServiceImpl;
import com.trganda.roadmap.rmi.impl.ServerSocketFactoryImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteServer {
    public static void main(String[] args) throws RemoteException, UnknownHostException {
        int registryPort = Integer.parseInt(args[0]);
        String registryIP = args[1];
        int serverPort = Integer.parseInt(args[2]);
        String serverIP = args[3];
        String name = args[4];

        RemoteServiceImpl rsi =
                new RemoteServiceImpl(
                        serverPort,
                        null,
                        new ServerSocketFactoryImpl(InetAddress.getByName(serverIP)));
        Registry reg = LocateRegistry.getRegistry(registryIP, registryPort);
        reg.rebind(name, rsi);
        System.out.println("rebind");
    }
}
