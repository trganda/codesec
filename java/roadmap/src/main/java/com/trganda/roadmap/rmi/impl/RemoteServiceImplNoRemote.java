package com.trganda.roadmap.rmi.impl;

import com.trganda.roadmap.rmi.remote.Message;
import com.trganda.roadmap.rmi.remote.RemoteService;

import java.rmi.RemoteException;

public class RemoteServiceImplNoRemote implements RemoteService {

    @Override
    public void doSome(String msg) throws RemoteException {
        System.out.println("[" + msg + "]");
    }

    @Override
    public void doSome(Message msg) throws RemoteException {
        System.out.println(msg);
    }

    @Override
    public void doSome(Object msg) throws RemoteException {
        System.out.println("call doSome(Object);");
    }
}
