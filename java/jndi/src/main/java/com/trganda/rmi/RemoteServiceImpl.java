package com.trganda.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServiceImpl extends UnicastRemoteObject
        implements RemoteService {
    public RemoteServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void doSome(String msg) throws RemoteException {
        System.out.println(msg);
    }
}