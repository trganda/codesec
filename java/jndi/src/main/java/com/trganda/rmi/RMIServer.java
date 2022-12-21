package com.trganda.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
    public void startListener(String name, Object obj) throws RemoteException, AlreadyBoundException {
        Registry reg = LocateRegistry.createRegistry(1099);
        // If the obj has no extens the UnicastRemoteObject, call exportObject
        // to return a stub
        if (!(obj instanceof UnicastRemoteObject)) {
            obj = UnicastRemoteObject.exportObject((Remote) obj);
        }
        reg.bind(name, (Remote) obj);
    }

    public static void main(String[] args) {
        try {
            RMIServer rs = new RMIServer();
            // Construct a remote server obj
            RemoteServiceImpl rsi = new RemoteServiceImpl();
            // binding to registry
            rs.startListener("server", rsi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
