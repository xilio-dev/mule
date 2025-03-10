package com.stackoak.stackoak.common.data.tag;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TagTypeEnum {
    SYSTEM(0, "系统"),
    USER(1, "用户"),;

    private final int type;
    private final String desc;

    // 静态哈希表，用于快速查找
    private static final Map<Integer, TagTypeEnum> TYPE_MAP = new HashMap<>();

    // 静态初始化块
    static {
        for (TagTypeEnum typeEnum : TagTypeEnum.values()) {
            TYPE_MAP.put(typeEnum.getType(), typeEnum);
        }
    }

    TagTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 通过 type 快速获取对应的 TagTypeEnum 枚举值
     *
     * @param type 类型值
     * @return 对应的 TagTypeEnum 枚举值，如果找不到则返回 null
     */
    public static TagTypeEnum getTypeByValue(int type) {
        return TYPE_MAP.get(type);
    }
}
