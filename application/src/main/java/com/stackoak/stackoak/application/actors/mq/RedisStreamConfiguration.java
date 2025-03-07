package com.stackoak.stackoak.application.actors.mq;

import com.stackoak.stackoak.application.mq.NotificationMessageListener;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Redis Stream 消息队列 配置类
 */
@Configuration
public class RedisStreamConfiguration {
    @Resource
    private RedisStreamUtil redisStreamUtil;
    @Resource
    private RedisMQProperties redisMQProperties;
    @Autowired
    private NotificationMessageListener notificationMessageListener;

    @Bean
    public List<Subscription> subscription(RedisConnectionFactory factory) {
        List<Subscription> resultList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(1);
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(), r -> {
            Thread thread = new Thread(r);
            thread.setName("async-stream-consumer-" + index.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        });
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainer
                        .StreamMessageListenerContainerOptions
                        .builder()
                        .batchSize(100)/*todo 单次处理消息数量*/
                        .executor(executor)
                        .pollTimeout(Duration.ofSeconds(2))
                        .errorHandler(new ErrorHandler() {
                            @Override
                            public void handleError(Throwable t) {
                                t.printStackTrace();
                            }
                        })
                        .build();
        for (RedisMQStream redisMqStream : redisMQProperties.getStreams()) {
            String streamName = redisMqStream.getName();
            RedisMQGroup redisMqGroup = redisMqStream.getGroups().get(0);
            initStream(streamName, redisMqGroup.getName());
            var listenerContainer = StreamMessageListenerContainer.create(factory, options);
            // 手动ask消息
            var subscription = listenerContainer.receive(Consumer.from(redisMqGroup.getName(), redisMqGroup.getConsumers()[0]),
                    StreamOffset.create(streamName, ReadOffset.lastConsumed()), notificationMessageListener);
            // 自动ask消息
          /*  var subscription = listenerContainer.receiveAutoAck(Consumer.from(redisMqGroup.getName(), redisMqGroup.getConsumers()[0]),
                    StreamOffset.create(streamName, ReadOffset.lastConsumed()), new ReportReadMqListener());*/
            resultList.add(subscription);
            listenerContainer.start();
        }
        return resultList;
    }

    private void initStream(String key, String group) {
        boolean hasKey = redisStreamUtil.hasKey(key);
        if (!hasKey) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("field", "value");
            //创建主题
            String result = redisStreamUtil.addMap(key, map);
            //创建消费组
            redisStreamUtil.createGroup(key, group);
            //将初始化的值删除掉
            redisStreamUtil.del(key, result);
        }
    }

}
