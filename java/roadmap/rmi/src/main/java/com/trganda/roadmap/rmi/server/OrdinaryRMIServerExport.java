package com.trganda.roadmap.rmi.server;

import com.trganda.roadmap.rmi.impl.RemoteServiceImplNoRemote;
import com.trganda.roadmap.rmi.impl.ServerSocketFactoryImpl;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class OrdinaryRMIServerExport {
    public static void main(String[] args) {
        try {
            int registryPort = Integer.parseInt(args[0]);
            int serverPort = Integer.parseInt(args[1]);
            String serverName = args[2];

            System.out.println(registryPort + " " + serverPort + " " + serverName);

            // RemoteServiceImplNoRemote has no extend the UnicastRemoteObject
            RemoteServiceImplNoRemote rsi = new RemoteServiceImplNoRemote();

            // refer:
            //   https://docs.oracle.com/javase/8/docs/api/java/rmi/registry/LocateRegistry.html
            // default listening port: 0.0.0.0:1099
            Registry reg = LocateRegistry.createRegistry(registryPort);
            // If the obj has no extends the UnicastRemoteObject, call exportObject
            // to return a stub
            rsi =
                    (RemoteServiceImplNoRemote)
                            UnicastRemoteObject.exportObject((Remote) rsi, serverPort);

            // rsi =
            //        (RemoteServiceImplNoRemote)
            //                UnicastRemoteObject.exportObject(
            //                        (Remote) rsi,
            //                        serverPort,
            //                        null,
            //                        new ServerSocketFactoryImpl(InetAddress.getByName(serverIP)));

            reg.bind(serverName, rsi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
