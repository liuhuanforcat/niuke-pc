package com.niukedemo.controller;

import com.niukedemo.entity.User;
import com.niukedemo.service.FollowService;
import com.niukedemo.util.CommunityUtil;
import com.niukedemo.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 刘欢
 * @date 2022年10月30日 15:30
 */
@Slf4j
@Controller
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private HostHolder hostHolder;

    /**
     * @Description:关注
     * @Param: [entityType, entityId]
     * @return: java.lang.String
     * @Author: 刘欢
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUsers();
        followService.follow(user.getId(), entityType, entityId);
        return CommunityUtil.getJsonString(0, "已关注！");
    }

    /**
     * @Description: 取消关注
     * @Param: [entityType, entityId]
     * @return: java.lang.String
     * @Author: 刘欢
     */
    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUsers();
        followService.unfollow(user.getId(), entityType, entityId);
        return CommunityUtil.getJsonString(0, "已取消关注！");
    }
}
