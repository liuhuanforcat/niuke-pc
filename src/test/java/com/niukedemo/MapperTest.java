package com.niukedemo;

import com.niukedemo.entity.*;
import com.niukedemo.mapper.*;
import com.niukedemo.util.CommunityUtil;
import com.niukedemo.util.HostHolder;
import com.niukedemo.util.SensitiveFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 刘欢
 * @date 2022年10月05日 20:04
 */

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NiukeApplication.class)
@SpringBootTest
public class MapperTest {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    private HostHolder hostHolder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Test
    public void selectById(){
        User user = userMapper.selectById(11);
        System.out.println(user);
        System.out.println("--------------------------");
        User aaa = userMapper.selectByName("aaa");
        System.out.println(aaa);
        System.out.println("--------------------------");
        User user1 = userMapper.selectByEmail("nowcoder1@sina.com");
        System.out.println(user1);
    }
    @Test
    public void InsertTest(){
        User user = new User();
        user.setUsername("liuhuan");
        user.setPassword("123456");
        user.setEmail("1057016137@11.com");
        user.setHeaderUrl("www.baidukc/01.png");
        user.setSalt("adc");
        user.setCreateTime(new Date());
        int i = userMapper.insertUser(user);
        System.out.println(i);
        System.out.println(">>>>>>>>>>>"+user.getId());
    }
    @Test
    public void updateTest(){
        int root = userMapper.updatePassword(1924665347, "root");
        System.out.println(root);
        System.out.println(">>>>>>>>>>");
        int i = userMapper.updateHeader(1924665347, "www.baidu.com/02.png");
        System.out.println(i);
        int i1 = userMapper.updateStatus(1924665347, 1);
        System.out.println(i1);
    }
    @Test
    public void pageTest(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(101, 0, 10);
        System.out.println(discussPosts);
        int i = discussPostMapper.selectDiscussPostRows(101);
        System.out.println(i);
    }
    @Test
    public void  testAddUserTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000*60*10));
        int i = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println(i);
    }
    @Test
    public void testSelectUserTicket(){
        LoginTicket abc = loginTicketMapper.selectByTicket("abc");
        System.out.println(abc);
        int abc1 = loginTicketMapper.updateStatus("abc", '1');
        LoginTicket abc2 = loginTicketMapper.selectByTicket("abc");
        System.out.println(abc2);
    }
    @Test
    public void  t1(){
        String title="xxx";
        String content="xxx";
        User user = userMapper.selectById(1924665361);
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
       discussPostMapper.insertDiscussPost(discussPost);
    }
    @Test
    public void t2(){
        Comment comment = new Comment();
        comment.setContent("历史丢恶风寒");
        comment.setUserId(1924665361);
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentMapper.insertComment(comment);
        int i = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
        discussPostMapper.updateCommentCount(0,i);
    }
    @Test
    public void messageT3(){
//        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
//        for (Message message:messages){
//            System.out.println(message);
//        }
//        int i = messageMapper.selectConversationCount(111);
//        System.out.println(i);
//        List<Message> messages1 = messageMapper.selectLetters("111_112", 0, 10);
//        for (Message message:messages1){
//            System.out.println(message);
//        }
//        int i1 = messageMapper.selectLetterCount("111_112");
//        System.out.println(i1);
        int i2 = messageMapper.selectLetterUnreadCount(131,"111_131");
        System.out.println(i2);
    }
    @Test
    public void t4(){
        Message message = messageMapper.selectLatestNotice(1924665361,"comment");
        System.out.println(message);
    }
}
