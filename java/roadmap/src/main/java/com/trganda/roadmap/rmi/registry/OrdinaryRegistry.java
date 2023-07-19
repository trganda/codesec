package com.trganda.roadmap.rmi.registry;

import com.trganda.roadmap.rmi.impl.ServerSocketFactoryImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrdinaryRegistry {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        String addr = args[1];

        // create a registry and listening
        Registry reg =
                LocateRegistry.createRegistry(
                        port, null, new ServerSocketFactoryImpl(InetAddress.getByName(addr)));

        // prevent stop current thread.
        System.in.read();
    }
}
