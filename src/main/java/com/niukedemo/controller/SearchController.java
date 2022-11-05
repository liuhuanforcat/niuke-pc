package com.niukedemo.controller;

import com.niukedemo.entity.DiscussPost;
import com.niukedemo.entity.Page;
import com.niukedemo.service.ElasticsearchService;
import com.niukedemo.service.LikeService;
import com.niukedemo.service.UserService;
import com.niukedemo.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘欢
 * @date 2022年11月05日 10:58
 */
@Controller
public class SearchController implements CommunityConstant {
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(String keyword, Page page, Model model){
        //搜索帖子
        List<DiscussPost> list = elasticsearchService.searchDiscussPost(keyword, page.getCurrent() - 1, page.getLimit());
        List<Map<String,Object>> discussPosts=new ArrayList<>();
        if (list!=null){
            for (DiscussPost post:list){
                Map<String,Object> map=new HashMap<>();
                //帖子
                map.put("post",post);
                //作者
                map.put("user",userService.findUserById(post.getUserId()));
                //点赞数量
                map.put("likeCount",likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()));
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        model.addAttribute("keyword",keyword);
        page.setPath("/search?keyword="+keyword);
        page.setRows(list==null?0:list.size());
        return "/site/search";
    }
}
