package com.stackoak.stackoak.common.data.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    COMMENT(1, "COMMENT"),
    REPLY(2, "REPLY"),
    LIKE(3, "LIKE"),
    COLLECT(4, "COLLECT"),
    FOLLOW(5, "FOLLOW"),
    SYSTEM(6, "system"),
    ;
    private final Integer type;
    private final String desc;

    NotificationType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
