package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.actors.security.SaUserCheckLogin;
import com.stackoak.stackoak.application.service.notification.INotificationSettingService;
import com.stackoak.stackoak.common.data.notification.NotificationSetting;
import com.stackoak.stackoak.common.data.notification.SettingNotifyRequest;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "通知设置")
@RestController
@RequestMapping("/notify-setting")
public class PortalNotificationSettingApi {

    @Autowired
    private INotificationSettingService ns;

    @PutMapping(value = "set", name = "设置消息通知")
    public Result set(@RequestBody SettingNotifyRequest request) {
        ns.setNotify(request);
        return Result.success();
    }
    @SaUserCheckLogin
    @GetMapping(value = "get-setting", name = "获取消息配置")
    public Result getUserNotifySetting() {
        NotificationSetting setting = ns.getUserNotifySetting();
        return Result.success(setting);
    }

}
