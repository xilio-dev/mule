package com.stackoak.stackoak.application.actors.mq;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Data
public class DelayedMessage {
    private String id;
    Map<String, Object> content;
    private String streamKey;
    private long executeTime; // 执行时间戳
    // 将时间戳转换为 Instant
    public Instant getExecuteInstant() {
        return Instant.ofEpochMilli(executeTime);
    }

    // 将时间戳转换为 LocalDateTime（默认系统时区）
    public LocalDateTime getExecuteLocalDateTime() {
        return LocalDateTime.ofInstant(getExecuteInstant(), ZoneId.systemDefault());
    }

    // 设置时间戳（通过 Instant）
    public void setExecuteInstant(Instant instant) {
        this.executeTime = instant.toEpochMilli();
    }

    // 设置时间戳（通过 LocalDateTime）
    public void setExecuteLocalDateTime(LocalDateTime localDateTime) {
        this.executeTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
