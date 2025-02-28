package com.stackoka.stackoka.application.controller;

import com.stackoka.stackoka.application.service.friendlink.IFriendLinkService;
import com.stackoka.stackoka.common.data.friendlink.FriendLink;
import com.stackoka.stackoka.common.message.RestResult;
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
@RestController
@RequestMapping("friendlink")
public class FriendLinkController {
    @Autowired
    private IFriendLinkService friendLinkService;
    @GetMapping("list")
    public RestResult list() {
        List<FriendLink> list = friendLinkService.listByPage();
        return RestResult.success(list);
    }
}
