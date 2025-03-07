package com.stackoak.stackoak.application.actors.mq;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Redis Stream工具类
 */
@Component
public class RedisStreamUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public String createGroup(String key, String group) {
        return stringRedisTemplate.opsForStream().createGroup(key, group);
    }


    public StreamInfo.XInfoConsumers queryConsumers(String key, String group) {
        return stringRedisTemplate.opsForStream().consumers(key, group);
    }

    public String addMap(String key, Map<String, Object> value) {
        return stringRedisTemplate.opsForStream().add(key, value).getValue();
    }


    public List<MapRecord<String, Object, Object>> read(String key) {
        return stringRedisTemplate.opsForStream().read(StreamOffset.fromStart(key));
    }

    public Long ack(String key, String group, String... recordIds) {
        return stringRedisTemplate.opsForStream().acknowledge(key, group, recordIds);
    }

    public Long del(String key, String... recordIds) {
        return stringRedisTemplate.opsForStream().delete(key, recordIds);
    }

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }
}
