package org.trganda.remote;

import java.rmi.RemoteException;

public interface RemoteService extends java.rmi.Remote{
    public void doSome(String msg) throws RemoteException;

    public void doSome(Message msg) throws RemoteException;

    public void doSome(Object msg) throws RemoteException;
}
