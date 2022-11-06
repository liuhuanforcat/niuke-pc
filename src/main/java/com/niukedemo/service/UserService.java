package com.niukedemo.service;

import com.niukedemo.entity.LoginTicket;
import com.niukedemo.entity.User;
import com.niukedemo.mapper.LoginTicketMapper;
import com.niukedemo.mapper.UserMapper;
import com.niukedemo.util.CommunityConstant;
import com.niukedemo.util.CommunityUtil;
import com.niukedemo.util.MailClient;
import com.niukedemo.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘欢
 * @date 2022年10月06日 16:08
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${community.path.domain}")
    private String domain;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public User findUserById(int id) {
//        return userMapper.selectById(id);
        User user = getCache(id);
        if (user == null) {
            user = initCache(id);
        }
        return user;
    }

    /**
     * @Description: 注册功能
     * @Param: [user]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: 刘欢
     */
    public Map<String, Object> register(User user) {
        HashMap<String, Object> map = new HashMap<>();
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空！");
            return map;
        }


        //验证账号
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "此账号已存在！");
            return map;
        }
        //验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg", "邮箱已经被注册过了！");
            return map;
        }


        //开始注册用户
        //1.设置盐，从自己1封装的工具类中的随机生成字符方法串截取前五位
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        //2。对前端收到的参数进行md5（自己封装的工具类中的方法）加密,为了保证数据安全性，用盐做后缀
        user.setPassword(CommunityUtil.md5(user.getPassword()) + user.getSalt());
        //用户默认为普通用户
        user.setType(0);
        //未激活状态，需要邮箱激活
        user.setStatus(0);
        //随机给一个激活码
        user.setActivationCode(CommunityUtil.generateUUID());
        //给一个默认的头像地址
        user.setHeaderUrl(String.format("http://images.newcoder.com/head/%dt.png", new Random().nextInt(1000)));
        //当前注册时间
        user.setCreateTime(new Date());
        userMapper.insertUser(user);


        //激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        //激活路径
        String url = domain + contextPath + "/activation" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("path", url);
        String process = templateEngine.process("/mail/activation", context);
        System.out.println(process);
        mailClient.sendMail(user.getEmail(), "激活邮件", process);

        return map;
    }

    /**
     * @Description: 发送的邮件有一个激活链接，此方法是激活判定
     * @Param: [userId, code]
     * @return: int
     * @Author: 刘欢
     */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
//            userMapper.updateStatus(userId, 1);
//            clearCache(userId);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    /**
     * @Description: 用户登录
     * @Param: [username, password, expiredSeconds]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: 刘欢
     */
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        HashMap<String, Object> map = new HashMap<>();
        //空值处理
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        } else if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        //验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活");
            return map;
        }
        String password1 = CommunityUtil.md5(password) + user.getSalt();
        if (!user.getPassword().equals(password1)) {
            map.put("passwordMsg", "密码输入错误");
            return map;
        }
        //登录已成功，生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        //loginTicketMapper.insertLoginTicket(loginTicket);
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    /**
     * @Description: 用户退出功能，修改其状态为无效
     * @Param: []
     * @return: void
     * @Author: 刘欢
     */
    public void logout(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);

//        loginTicketMapper.updateStatus(ticket, 1);

    }

    public LoginTicket findLoginTicket(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
//        return loginTicketMapper.selectByTicket(ticket);
    }

    /**
     * @Description:修改头像
     * @Param: [userId, headerUrl]
     * @return: int
     * @Author: 刘欢
     */
    public int updateHeader(int userId, String headerUrl) {
        int row = userMapper.updateHeader(userId, headerUrl);
        clearCache(userId);
        return row;
    }

    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }

    //优先从缓存中取值
    private User getCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    //取不到值初始化缓存数据
    private User initCache(int userId) {
        User user = userMapper.selectById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);
        return user;
    }

    //数据变更时清除缓存数据
    private void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        User user = this.findUserById(userId);
        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (user.getType()){
                    case 1:
                        return AUTHORITY_ADMIN;
                    case 2:
                        return AUTHORITY_MODERATOR;
                    default:
                        return AUTHORITY_USER;
                }
            }
        });
        return list;
    }

}
