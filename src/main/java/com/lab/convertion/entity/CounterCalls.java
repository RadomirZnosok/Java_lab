package com.lab.convertion.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterCalls {
    private static Integer counter = 0;
    private final AtomicInteger synhCounter = new AtomicInteger(0);
    public CounterCalls(){};
    public void increaseCounter(){
        counter++;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        CounterCalls.counter = counter;
    }

    public void increaseSynhCounter(){
        synhCounter.incrementAndGet();
    }

    public Integer getSynhCounter() {
        return synhCounter.get();
    }

    public void setSynhCounter(Integer synhCounter) {
        this.synhCounter.set(synhCounter);
    }
}
