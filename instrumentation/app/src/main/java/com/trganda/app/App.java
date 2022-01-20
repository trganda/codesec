package com.trganda.app;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App {

    public void run() throws InterruptedException {
        Logger logger = LogManager.getLogger(App.class);
        logger.info("App Running ... ");
        Thread.sleep(1000L);
    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        while(true) {
            app.run();
            Thread.sleep(3000L);
        }
    }
}
