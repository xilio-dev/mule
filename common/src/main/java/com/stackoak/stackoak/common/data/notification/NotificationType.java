package com.stackoak.stackoak.common.data.notification;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum NotificationType {
    COMMENT(1, "COMMENT"),
    LIKE_COLLECT(2, "LIKE_COLLECT"),
    FOLLOW(3, "FOLLOW"),
    CHAT(4, "chat");

    private final int type;
    private final String desc;

    // 静态哈希表，用于快速查找
    private static final Map<Integer, NotificationType> TYPE_MAP = new HashMap<>();

    // 静态初始化块
    static {
        for (NotificationType notificationType : NotificationType.values()) {
            TYPE_MAP.put(notificationType.getType(), notificationType);
        }
    }

    NotificationType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    /**
     * 通过 type 快速获取对应的 NotificationType 枚举值
     *
     * @param type 类型值
     * @return 对应的 NotificationType 枚举值，如果找不到则返回 null
     */
    public static NotificationType getTypeByValue(int type) {
        return TYPE_MAP.get(type);
    }
}
