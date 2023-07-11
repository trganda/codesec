package com.trganda.roadmap.rmi.server;

import com.trganda.roadmap.rmi.impl.RemoteServiceImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class OrdinaryRMIServer {

    public void startListener(String name, Object obj)
            throws RemoteException, AlreadyBoundException {
        Registry reg = LocateRegistry.createRegistry(1099);
        // If the obj has no extends the UnicastRemoteObject, call exportObject
        // to return a stub
        if (!(obj instanceof UnicastRemoteObject)) {
            obj = UnicastRemoteObject.exportObject((Remote) obj, 56050);
        }
        reg.bind(name, (Remote) obj);
    }

    public static void main(String[] args) {
        try {
            OrdinaryRMIServer rs = new OrdinaryRMIServer();
            // explicitly set the port of server
            RemoteServiceImpl rsi = new RemoteServiceImpl(56050);
            rs.startListener("server", rsi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
