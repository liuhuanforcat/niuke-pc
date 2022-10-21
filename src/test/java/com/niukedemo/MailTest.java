package com.niukedemo;

import com.niukedemo.util.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.event.MailEvent;

/**
 * @author 刘欢
 * @date 2022年10月07日 23:04
 */

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NiukeApplication.class)
@SpringBootTest
public class MailTest {
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {

        mailClient.sendMail("2825258377@qq.com", "嘉怡大美女", "嘉怡大美女");
    }
    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","刘欢大帅哥");
        String process = templateEngine.process("/mail/demo", context);
        System.out.println(process);
        mailClient.sendMail("2825258377@qq.com","嘉",process);
    }

}
