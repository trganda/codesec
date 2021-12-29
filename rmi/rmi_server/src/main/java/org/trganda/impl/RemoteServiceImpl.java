package org.trganda.impl;

import org.trganda.remote.RemoteService;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServiceImpl extends UnicastRemoteObject
        implements RemoteService {
    public RemoteServiceImpl() throws RemoteException {}

    public RemoteServiceImpl(int port) throws RemoteException {
        super(port);
    }

    public RemoteServiceImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void doSome(String msg) throws RemoteException {
        System.out.println(msg);
    }
}
