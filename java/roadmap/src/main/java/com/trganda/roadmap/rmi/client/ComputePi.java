package com.trganda.roadmap.rmi.client;

import com.trganda.roadmap.rmi.impl.Pi;
import com.trganda.roadmap.rmi.remote.Compute;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ComputePi {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        String addr = (args[1]);
        String name = args[2];
        String params = args[3];

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry(addr, port);
            Compute comp = (Compute) registry.lookup(name);
            Pi task = new Pi(Integer.parseInt(params));
            BigDecimal pi = comp.executeTask(task);
            System.out.println(pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
