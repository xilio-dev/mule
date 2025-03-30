package com.stackoak.stackoak.common.data.announcement;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum AnnouncementType {
    SYSTEM(1, "SYSTEM", "系统公告"),
    EVENT(2, "EVENT", "活动公告"),
    POLICY(3, "POLICY", "政策公告"),
    COMMUNITY(4, "COMMUNITY", "社区公告"),
    EMERGENCY(5, "EMERGENCY", "紧急公告");

    private final int code;        // 数据库中的值 (tinyint)
    private final String key;      // 格式中的键 (如 "SYSTEM")
    private final String label;    // 显示标签 (如 "系统公告")

    // 缓存 code 到 AnnouncementType 的映射
    private static final Map<Integer, AnnouncementType> CODE_MAP;

    static {
        Map<Integer, AnnouncementType> map = new HashMap<>();
        for (AnnouncementType type : values()) {
            map.put(type.getCode(), type);
        }
        // 使用不可变 Map，提高安全性并防止意外修改
        CODE_MAP = Collections.unmodifiableMap(map);
    }

    AnnouncementType(int code, String key, String label) {
        this.code = code;
        this.key = key;
        this.label = label;
    }

    // 根据 code 获取枚举，O(1) 时间复杂度
    public static AnnouncementType fromCode(int code) {
        AnnouncementType type = CODE_MAP.get(code);
        if (type == null) {
            throw new IllegalArgumentException("Invalid announcement type code: " + code);
        }
        return type;
    }
}
