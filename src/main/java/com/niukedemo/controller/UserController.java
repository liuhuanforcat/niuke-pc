package com.niukedemo.controller;

import com.niukedemo.annotation.LoginRequired;
import com.niukedemo.entity.User;
import com.niukedemo.service.LikeService;
import com.niukedemo.service.UserService;
import com.niukedemo.util.CommunityUtil;
import com.niukedemo.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author 刘欢
 * @date 2022年10月12日 22:15
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Value("${community.path.upload}")
    private String uploadPath;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private LikeService likeService;

    /**
     * @Description: 访问setting.html
     * @Param: []
     * @return: java.lang.String
     * @Author: 刘欢
     */
    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    /**
     * @Description:文件上传功能
     * @Param: [headerImage, model]
     * @return: java.lang.String
     * @Author: 刘欢
     */
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "/site/setting";
        }
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isEmpty(suffix)) {
            model.addAttribute("error", "文件格式不正确");
            return "/site/setting";
        }
        //生成随机文件名进行存储
        filename = CommunityUtil.generateUUID() + suffix;
        //确定文件存放的位置
        File file = new File(uploadPath + "/" + filename);
        try {
            headerImage.transferTo(file);
        } catch (IOException e) {
            log.error("文件上传失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常", e);
        }
        //更新用户头像（web）路径
        //http://localsost/community/user/header/xxx.png
        User users = hostHolder.getUsers();
        String headerUrl = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(users.getId(), headerUrl);
        return "redirect:/index";
    }

    /**
     * @Description: 图片响应给浏览器
     * @Param: [fileName, response]
     * @return: void
     * @Author: 刘欢
     */
    @RequestMapping(value = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        response.setContentType("image/" + suffix);
        try (FileInputStream fis = new FileInputStream(fileName);
             OutputStream os = response.getOutputStream();) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            log.error("读取头像失败" + e.getMessage());
        }
    }

    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }
        //存储用户
        model.addAttribute("user", user);
        //存储点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);
        return "/site/profile";
    }
}
