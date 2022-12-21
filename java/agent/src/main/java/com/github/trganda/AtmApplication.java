package com.github.trganda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtmApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AtmApplication.class);

    public static void run(String[] args) throws Exception {
        LOGGER.info("[Application] Starting ATM application");
        Atm.withdrawMoney(2);

        Thread.sleep(10000L);

        Atm.withdrawMoney(3);
    }

}