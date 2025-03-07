package com.stackoak.stackoak.application.mq;

import com.stackoak.stackoak.application.actors.mq.RedisStreamUtil;
import com.stackoak.stackoak.application.service.notification.SseClient;
import com.stackoak.stackoak.common.data.notification.CommentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息推送监听器
 */
@Component
public class NotificationMessageListener implements StreamListener<String, MapRecord<String, String, String>> {
    @Autowired
    private RedisStreamUtil redisStreamUtil;
    @Autowired
    private SseClient sseClient;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        String streamKey = message.getStream();
        //消息ID
        RecordId recordId = message.getId();
        //消息内容
        Map<String, String> msg = message.getValue();
        String userId = msg.get("userId");
        String content = msg.get("content");
        CommentMessage commentMessage = new CommentMessage();
        commentMessage.setMessageId(streamKey);
        commentMessage.setContent(content);
        sseClient.sendMessage(userId, commentMessage);
        //redisStreamUtil.ack(streamKey,"NOTIFICATION_GROUP",recordId.getValue());
        //   redisStreamUtil.del(streamKey,recordId.getValue());
        System.out.println("MessageId: " + message.getId());
        System.out.println("Stream: " + message.getStream());
        System.out.println("Body: " + message.getValue());
    }
}
