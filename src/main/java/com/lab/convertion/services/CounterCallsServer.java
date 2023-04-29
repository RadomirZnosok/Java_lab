package com.lab.convertion.services;

import com.lab.convertion.controllers.ControllerConvert;
import com.lab.convertion.entity.CounterCalls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CounterCallsServer {
    private static final Logger logger = LoggerFactory.getLogger(ControllerConvert.class);
    private final CounterCalls counter = new CounterCalls();
    public void increase(){
        if (counter.getCounter().equals(Integer.MAX_VALUE)){
            logger.warn("Static counter overflow. The counter is reset to zero");
            counter.setCounter(1);
        }
        else {
            counter.increaseCounter();
        }
        if (counter.getSynhCounter().equals(Integer.MAX_VALUE)){
            logger.warn("Atomic counter overflow. The counter is reset to zero");
            counter.setSynhCounter(1);
        }
        else {
            counter.increaseSynhCounter();
        }
        logger.info("Count of calls to the service is " + counter.getCounter() + "(static Integer)");
        logger.info("Count of calls to the service is " + counter.getSynhCounter() + "(AtomicInteger)");
    }
    public CounterCalls getCounter() {
        return this.counter;
    }
}
