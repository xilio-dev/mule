package com.stackoak.stackoak.application.mq;

import com.stackoak.stackoak.application.actors.mq.DelayedMessage;
import com.stackoak.stackoak.application.actors.mq.RedisStreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

//@Service
//public class DelayedMessageProcessor {
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    private RedisStreamUtil redisStreamUtil;
//    private static final String DELAYED_QUEUE = "delayed_queue";
//    @Scheduled(fixedRate = 1000) // 每秒执行一次
//    public void processDelayedMessages() {
//
//        long currentTime = System.currentTimeMillis();
//        // 获取到期的消息
//        Set<ZSetOperations.TypedTuple<Object>> expiredMessages = redisTemplate.opsForZSet()
//                .rangeByScoreWithScores(DELAYED_QUEUE, 0, currentTime);
//
//        for (ZSetOperations.TypedTuple<Object> tuple : expiredMessages) {
//            DelayedMessage delayedMessage = (DelayedMessage) tuple.getValue();
//            // 推送到 Redis Stream
//            redisStreamUtil.addMap(delayedMessage.getStreamKey(), delayedMessage.getContent());
//            // 从 Sorted Set 中移除
//            redisTemplate.opsForZSet().remove(DELAYED_QUEUE, delayedMessage);
//        }
//    }
//}
@Service
public class DelayedMessageProcessor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String DELAYED_QUEUE = "delayed_queue";
    private static final String LUA_SCRIPT = "local expiredMessages = redis.call('ZRANGEBYSCORE', KEYS[1], 0, ARGV[1])\n" +
            "for i, message in ipairs(expiredMessages) do\n" +
            "    local messageData = cjson.decode(message)\n" +
            "    local streamKey = messageData['streamKey']\n" +
            "    local content = messageData['content']\n" +
            "    redis.call('XADD', streamKey, '*', 'content', content)\n" +
            "    redis.call('ZREM', KEYS[1], message)\n" +
            "end\n" +
            "return #expiredMessages";

    @Scheduled(fixedRate = 1000) // 每秒执行一次
    public void processDelayedMessages() {
        long currentTime = System.currentTimeMillis();
        // 执行 Lua 脚本
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);
        Long processedCount = redisTemplate.execute(script, Collections.singletonList(DELAYED_QUEUE), String.valueOf(currentTime));
        if (processedCount != null && processedCount > 0) {
            System.out.println("Processed " + processedCount + " messages.");
        }
    }
}
