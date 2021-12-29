package org.trganda.server;

import org.trganda.impl.RemoteServiceImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

    public void startListener(String name, Object obj) throws RemoteException, AlreadyBoundException {
        Registry reg = LocateRegistry.createRegistry(9099);

        if (!(obj instanceof UnicastRemoteObject)) {
           obj = UnicastRemoteObject.exportObject((Remote) obj, 0);
        }
        reg.bind(name, (Remote) obj);
    }

    public static void main(String[] args) {
        try {
            RMIServer rs = new RMIServer();
            RemoteServiceImpl rsi = new RemoteServiceImpl();
            rs.startListener("server", rsi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
