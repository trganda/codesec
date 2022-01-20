package com.trganda.dynamicagent;

import com.trganda.transformer.DurationTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.instrument.Instrumentation;

public class DurationAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        Logger logger = LogManager.getLogger(DurationAgent.class);

        logger.info("Executing premain.........");
        inst.addTransformer(new DurationTransformer());
    }
}
