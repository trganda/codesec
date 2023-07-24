package com.trganda.roadmap.rmi.server;

import com.trganda.roadmap.rmi.remote.Compute;
import com.trganda.roadmap.rmi.remote.Task;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements Compute {

    public ComputeEngine() {
        super();
    }

    public <T> T executeTask(Task<T> t) {
        return t.execute();
    }

    public static void main(String[] args) {
        int registryPort = Integer.parseInt(args[0]);
        String registryIP = args[1];
        int serverPort = Integer.parseInt(args[2]);
        String name = args[3];

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Compute engine = new ComputeEngine();
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, serverPort);
            Registry registry = LocateRegistry.getRegistry(registryIP, registryPort);
            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}
