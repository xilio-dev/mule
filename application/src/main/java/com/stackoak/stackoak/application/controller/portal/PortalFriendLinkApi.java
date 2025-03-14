package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.friendlink.IFriendLinkService;
import com.stackoak.stackoak.common.data.friendlink.FriendLink;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-28 15:46:24
 */
@Tag(name = "友情链接")
@RestController
@RequestMapping("/friendlink")
public class PortalFriendLinkApi {
    @Autowired
    private IFriendLinkService friendLinkService;
    @GetMapping("list")
    public Result list() {
        List<FriendLink> list = friendLinkService.listByPage();
        return Result.success(list);
    }
}
