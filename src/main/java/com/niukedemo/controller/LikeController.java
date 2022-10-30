package com.niukedemo.controller;

import com.niukedemo.entity.User;
import com.niukedemo.service.LikeService;
import com.niukedemo.util.CommunityUtil;
import com.niukedemo.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘欢
 * @date 2022年10月28日 21:10
 */
@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId,int entityUserId) {
        User user = hostHolder.getUsers();
        //点赞功能
        likeService.like(user.getId(), entityType, entityId,entityUserId);
        //数量
        long LikeCount = likeService.findEntityLikeCount(entityType, entityId);
        //状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        //返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", LikeCount);
        map.put("likeStatus", likeStatus);
        return CommunityUtil.getJsonString(0, null, map);
    }

}
