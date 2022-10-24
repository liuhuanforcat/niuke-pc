package com.niukedemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 刘欢
 * @date 2022年10月22日 21:29
 */
@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NiukeApplication.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void t1(){
        redisTemplate.opsForValue().set("s1","1");
        Object s1 = redisTemplate.opsForValue().get("s1");
        System.out.println("s1="+s1);
    }
}
