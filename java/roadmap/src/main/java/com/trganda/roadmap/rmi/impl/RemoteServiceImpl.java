package com.trganda.roadmap.rmi.impl;

import com.trganda.roadmap.rmi.remote.Message;
import com.trganda.roadmap.rmi.remote.RemoteService;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServiceImpl extends UnicastRemoteObject implements RemoteService {
  public RemoteServiceImpl() throws RemoteException {}

  public RemoteServiceImpl(int port) throws RemoteException {
    super(port);
  }

  public RemoteServiceImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf)
      throws RemoteException {
    super(port, csf, ssf);
  }

  @Override
  public void doSome(String msg) throws RemoteException {
    System.out.println(msg);
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
