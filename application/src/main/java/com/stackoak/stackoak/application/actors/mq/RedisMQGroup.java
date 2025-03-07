package com.stackoak.stackoak.application.actors.mq;

public class RedisMQGroup {


    private String name;

    private String[] consumers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getConsumers() {
        return consumers;
    }

    public void setConsumers(String[] consumers) {
        this.consumers = consumers;
    }
}
