package org.trganda.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.trganda.remote.RemoteService;

public class RMIClient {

    public void callRemote(String name) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9099);
        RemoteService rs = (RemoteService) registry.lookup(name);

        rs.doSome("call rmi server.");
    }

    public static void main(String[] args) {
        try {
            RMIClient rc = new RMIClient();

            rc.callRemote("server");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
