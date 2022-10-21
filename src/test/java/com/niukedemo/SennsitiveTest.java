package com.niukedemo;

import com.niukedemo.util.SensitiveFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 刘欢
 * @date 2022年10月15日 10:50
 */

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NiukeApplication.class)
@SpringBootTest
public class SennsitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public void SensitiveFilterTest(){
        String c="这里可以@吸@毒，还可以嫖娼，还能开票，嘿嘿嘿！";
        String filter = sensitiveFilter.filter(c);
        System.out.println(filter);
    }
}
