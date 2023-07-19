package com.trganda.roadmap.rmi.server;

import com.trganda.roadmap.rmi.impl.RemoteServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrdinaryRMIServer {
    public static void main(String[] args) {
        try {
            int registryPort = Integer.parseInt(args[0]);
            int serverPort = Integer.parseInt(args[1]);
            String serverName = args[2];

            System.out.println(registryPort + " " + serverPort + " " + serverName);

            // explicitly set the port of server
            RemoteServiceImpl rsi = new RemoteServiceImpl(serverPort);

            // refer:
            //   https://docs.oracle.com/javase/8/docs/api/java/rmi/registry/LocateRegistry.html
            // default listening port: 0.0.0.0:1099
            Registry reg = LocateRegistry.createRegistry(registryPort);
            // If the obj has no extends the UnicastRemoteObject, call exportObject
            // to return a stub
            // if (!(obj instanceof UnicastRemoteObject)) {
            //    obj = UnicastRemoteObject.exportObject((Remote) obj, serverPort);
            // }

            reg.bind(serverName, rsi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
