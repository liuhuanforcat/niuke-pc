package com.niukedemo.controller;

import com.niukedemo.entity.Event;
import com.niukedemo.entity.Page;
import com.niukedemo.entity.User;
import com.niukedemo.event.EventProducer;
import com.niukedemo.service.FollowService;
import com.niukedemo.service.UserService;
import com.niukedemo.util.CommunityConstant;
import com.niukedemo.util.CommunityUtil;
import com.niukedemo.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 刘欢
 * @date 2022年10月30日 15:30
 */
@Slf4j
@Controller
public class FollowController implements CommunityConstant {
    @Autowired
    private FollowService followService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private EventProducer eventProducer;


    /**
     * @Description:关注
     * @Param: [entityType, entityId]
     * @return: java.lang.String
     * @Author: 刘欢
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUsers();
        followService.follow(user.getId(), entityType, entityId);
        Event event = new Event()
                .setTopic(TOPIC_FOLLOW)
                .setUserId(hostHolder.getUsers().getId())
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setEntityUserId(entityId);
        eventProducer.fireEvent(event);
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

    @RequestMapping(value = "/followees/{userId}", method = RequestMethod.GET)
    public String getFollowees(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }
        model.addAttribute("user", user);
        page.setLimit(5);
        page.setPath("/followees/" + userId);
        page.setRows((int) followService.findFolloweeCount(userId, CommunityConstant.ENTITY_TYPE_POST));
        List<Map<String, Object>> userlist = followService.findFollowees(userId, page.getOffset(), page.getLimit());
        if (userlist != null) {
            for (Map<String, Object> map : userlist) {
                User u = (User) map.get("user");
                map.put("hasFollowed",hasFollowed(u.getId()));
            }
        }
        model.addAttribute("users",userlist);
        return "/site/followee";
    }


    @RequestMapping(value = "/followers/{userId}", method = RequestMethod.GET)
    public String getFollowers(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }
        model.addAttribute("user", user);
        page.setLimit(5);
        page.setPath("/followers/" + userId);
        page.setRows((int) followService.findFollowerCount(CommunityConstant.ENTITY_TYPE_USER,userId));
        List<Map<String, Object>> userlist = followService.findFollowers(userId, page.getOffset(), page.getLimit());
        if (userlist != null) {
            for (Map<String, Object> map : userlist) {
                User u = (User) map.get("user");
                map.put("hasFollowed",hasFollowed(u.getId()));
            }
        }
        model.addAttribute("users",userlist);
        return "/site/follower";
    }







    private boolean hasFollowed(int userId) {
        if (hostHolder.getUsers() == null) {
            return false;
        }
        return followService.hasFollowed(hostHolder.getUsers().getId(), CommunityConstant.ENTITY_TYPE_USER, userId);
    }
}
