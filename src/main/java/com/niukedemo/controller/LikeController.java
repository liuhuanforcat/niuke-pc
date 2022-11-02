package com.niukedemo.controller;

import com.niukedemo.entity.Event;
import com.niukedemo.entity.User;
import com.niukedemo.event.EventProducer;
import com.niukedemo.service.LikeService;
import com.niukedemo.util.CommunityConstant;
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
public class LikeController implements CommunityConstant {
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;


    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId,int entityUserId,int postId) {
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
        //出发点赞事件
        if (likeStatus==1){
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUsers().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setData("postId",postId);
            eventProducer.fireEvent(event);
        }


        return CommunityUtil.getJsonString(0, null, map);
    }

}
