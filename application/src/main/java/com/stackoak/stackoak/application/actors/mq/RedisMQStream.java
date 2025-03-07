package com.stackoak.stackoak.application.actors.mq;

import java.util.List;

public class RedisMQStream {
    public String name;
    public List<RedisMQGroup> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RedisMQGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<RedisMQGroup> groups) {
        this.groups = groups;
    }
}
