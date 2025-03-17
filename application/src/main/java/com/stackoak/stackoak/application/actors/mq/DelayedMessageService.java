package com.stackoak.stackoak.application.actors.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DelayedMessageService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String DELAYED_QUEUE = "delayed_queue";

    public void addMessage(DelayedMessage message) {
        redisTemplate.opsForZSet().add(DELAYED_QUEUE, message, message.getExecuteTime());
    }
}
