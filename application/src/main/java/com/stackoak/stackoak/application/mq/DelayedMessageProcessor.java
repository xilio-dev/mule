package com.stackoak.stackoak.application.mq;

import com.stackoak.stackoak.application.actors.mq.DelayedMessage;
import com.stackoak.stackoak.application.actors.mq.RedisStreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;@Service
public class DelayedMessageProcessor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisStreamUtil redisStreamUtil;
    private static final String DELAYED_QUEUE = "delayed_queue";
    @Scheduled(fixedRate = 100000) // 每10秒执行一次
    public void processDelayedMessages() {
        System.out.println("Processing delayed messages...");
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
      //  long currentTime = System.currentTimeMillis();
        // 获取到期的消息
        Set<ZSetOperations.TypedTuple<Object>> expiredMessages = redisTemplate.opsForZSet()
                .rangeByScoreWithScores(DELAYED_QUEUE, 0, currentTime);

        for (ZSetOperations.TypedTuple<Object> tuple : expiredMessages) {
            DelayedMessage delayedMessage = (DelayedMessage) tuple.getValue();
            // 推送到 Redis Stream
            redisStreamUtil.addMap(delayedMessage.getStreamKey(), delayedMessage.getContent());
            // 从 Sorted Set 中移除
            redisTemplate.opsForZSet().remove(DELAYED_QUEUE, delayedMessage);
        }
    }
}

