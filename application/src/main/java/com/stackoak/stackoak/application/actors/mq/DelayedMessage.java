package com.stackoak.stackoak.application.actors.mq;

import lombok.Data;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

@Data
public class DelayedMessage implements Serializable {
    private String id;
    Map<String, Object> content;
    private String streamKey;
    private long executeTime; // 执行时间戳
    public void setExecuteDateTime(int delaySeconds) {
        Calendar calendar = Calendar.getInstance(); // 获取当前时间
        calendar.add(Calendar.SECOND, delaySeconds); // 增加延迟秒数
        this.executeTime = calendar.getTimeInMillis(); // 转换为时间戳
    }
}
