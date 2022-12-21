package com.github.trganda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Atm {
    private static final Logger LOGGER = LoggerFactory.getLogger(Atm.class);

    private static final int account = 10;

    public static void withdrawMoney(int amount) throws InterruptedException {
        Thread.sleep(2000L); //processing going on here
        LOGGER.info("[Application] Successful Withdrawal of [{}] units!", amount);
    }
}