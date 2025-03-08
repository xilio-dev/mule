package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.notification.INotificationSettingService;
import com.stackoak.stackoak.common.data.notification.NotificationSetting;
import com.stackoak.stackoak.common.data.notification.SettingNotifyRequest;
import com.stackoak.stackoak.common.message.RestResult;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户通知设置表 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-08 15:44:39
 */
@RestController
@RequestMapping("/notify-setting")
public class PortalNotificationSettingApi {

    @Autowired
    private INotificationSettingService ns;

    @PutMapping(value = "set", name = "设置消息通知")
    public RestResult set(@RequestBody SettingNotifyRequest request) {
        ns.setNotify(request);
        return RestResult.success();
    }

    @GetMapping(value = "get-setting", name = "获取消息配置")
    public RestResult getUserNotifySetting() {
        NotificationSetting setting = ns.getUserNotifySetting();
        return RestResult.success(setting);
    }

}
