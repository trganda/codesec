package com.github.trganda;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if(args[0].equals("StartMyAtmApplication")) {
            new AtmApplication().run(args);
        } else if(args[0].equals("LoadAgent")) {
            new AgentLoader().run(args);
        }
    }
}