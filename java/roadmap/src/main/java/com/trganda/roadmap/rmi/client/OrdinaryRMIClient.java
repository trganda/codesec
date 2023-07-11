package com.trganda.roadmap.rmi.client;

import com.trganda.roadmap.rmi.remote.RemoteService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrdinaryRMIClient {

    public void callRemote(String name) throws RemoteException, NotBoundException {
        // default host: localhost; port: 1099
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        RemoteService rs = (RemoteService) registry.lookup(name);

        rs.doSome("call rmi server.");
    }

    public static void main(String[] args) {
        try {
            OrdinaryRMIClient rc = new OrdinaryRMIClient();

            rc.callRemote("server");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
