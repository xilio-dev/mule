package com.stackoak.stackoak.application.actors.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "redis.mq")
public class RedisMQProperties {
    public List<RedisMQStream> streams;

    public List<RedisMQStream> getStreams() {
        return streams;
    }

    public void setStreams(List<RedisMQStream> streams) {
        this.streams = streams;
    }
}
