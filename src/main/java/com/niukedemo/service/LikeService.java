package com.niukedemo.service;

import com.niukedemo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 点赞的业务
 *
 * @author 刘欢
 * @date 2022年10月24日 9:03
 */
@Service
public class LikeService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate1;

    public void like(int userId, int entityType, int entityId) {
        String useId=String.valueOf(userId);
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, useId);
        //判断是否点过赞
        if (isMember){
            redisTemplate.opsForSet().remove(entityLikeKey,useId);
        }else {
            redisTemplate.opsForSet().add(entityLikeKey, useId);
        }

    }

}
