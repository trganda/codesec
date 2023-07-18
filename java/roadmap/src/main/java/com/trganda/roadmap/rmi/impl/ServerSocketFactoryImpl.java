package com.trganda.roadmap.rmi.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

public class ServerSocketFactoryImpl implements RMIServerSocketFactory {

    private final InetAddress bindAddr;

    public ServerSocketFactoryImpl(InetAddress bindAddr) {
        this.bindAddr = bindAddr;
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port, 0, bindAddr);
    }

    /*
     * https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html
     *
     * An implementation of this interface should implement Object.equals(java.lang.Object)
     * to return true when passed an instance that represents the same
     * (functionally equivalent) server socket factory, and false otherwise
     * (and it should also implement Object.hashCode() consistently with
     * its Object.equals implementation).
     */
    @Override
    public boolean equals ( Object obj )
    {
        return obj != null && this.getClass() == obj.getClass() && this.bindAddr.equals( ((ServerSocketFactoryImpl)obj).bindAddr );
    }

}
