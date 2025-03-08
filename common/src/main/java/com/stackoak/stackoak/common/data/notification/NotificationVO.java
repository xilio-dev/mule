package com.stackoak.stackoak.common.data.notification;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class NotificationVO implements Serializable {
    private List<MessageItem> items;
    private Integer unReadCount;
}
