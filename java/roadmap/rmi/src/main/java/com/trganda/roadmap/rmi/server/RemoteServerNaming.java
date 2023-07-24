package com.trganda.roadmap.rmi.server;

import com.trganda.roadmap.rmi.impl.RemoteServiceImpl;
import com.trganda.roadmap.rmi.impl.ServerSocketFactoryImpl;

import java.net.InetAddress;
import java.rmi.Naming;

public class RemoteServerNaming {
    public static void main(String[] args) throws Exception {
        int registryPort = Integer.parseInt(args[0]);
        String registryIP = args[1];
        int serverPort = Integer.parseInt(args[2]);
        String serverIP = args[3];
        String name = args[4];

        System.out.println(
                registryPort + " " + registryIP + " " + serverPort + " " + serverIP + " " + name);

        String url = String.format("rmi://%s:%d/%s", registryIP, registryPort, name);

        RemoteServiceImpl rsi =
                new RemoteServiceImpl(
                        serverPort,
                        null,
                        new ServerSocketFactoryImpl(InetAddress.getByName(serverIP)));
        Naming.rebind(url, rsi);
    }
}
