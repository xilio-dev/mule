package com.stackoak.stackoak.application.actors.recommand;

import lombok.Getter;

@Getter
public enum BehaviorType {
    ViEW("view", "阅读"),
    LIKE("like", "like"),
    COMMENT("comment", "comment"),
    COLLECT("collect", "collect"),
    SHARE("share", "share"),
    ;

    BehaviorType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private final String type;
    private final String desc;
}
