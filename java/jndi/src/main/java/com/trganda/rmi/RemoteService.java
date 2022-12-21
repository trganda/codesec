package com.trganda.rmi;

import java.rmi.RemoteException;

public interface RemoteService extends java.rmi.Remote{
    public void doSome(String msg) throws RemoteException;
}
