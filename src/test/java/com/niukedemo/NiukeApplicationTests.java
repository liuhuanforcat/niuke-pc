package com.niukedemo;

import com.niukedemo.entity.DiscussPost;
import com.niukedemo.entity.User;
import com.niukedemo.mapper.DiscussPostMapper;
import com.niukedemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NiukeApplication.class)
@SpringBootTest
class NiukeApplicationTests {
    @Autowired
   private DiscussPostMapper discussPostMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }
    @Test
    void testUserSelectById(){
        log.info("userSelectById就绪");
        User user = userMapper.selectById(11);
        log.info("userSelectById完成");
        System.out.println(user);
    }
    @Test
    void testSelectDiscussPostRows(){
        int i = discussPostMapper.selectDiscussPostRows(109);
        System.out.println(i);
        System.out.println();
    }
}
