package com.stackoak.stackoak.common.data.notification;

import lombok.Data;

import java.io.Serializable;

@Data
public class SettingNotifyRequest implements Serializable {
    /**
     * 通知类型
     */
    private Integer type;
    /**
     * 通知开启状态
     */
    private Boolean enabled;
}
