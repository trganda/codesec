package com.trganda.dynamicagent;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

public class Append {
    public static void main(String[] args) {
        try {
            // attach to target VM
//            VirtualMachine vm = VirtualMachine.attach(args[1]);
//
//            // load agent jar file
//            vm.loadAgentPath(args[2]);
//            vm.detach();
            List<VirtualMachineDescriptor> vms = VirtualMachine.list();
            for (VirtualMachineDescriptor vmd : vms) {
                System.out.println(vmd.displayName());
                if (vmd.displayName().endsWith("app-1.0.jar")) {
                    VirtualMachine vm = VirtualMachine.attach(vmd.id());
                    vm.loadAgent("D:\\Projects\\github\\codecurtiy\\instrumentation\\agent\\target\\agent-1.0.jar");
                    vm.detach();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
