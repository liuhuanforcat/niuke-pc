package com.niukedemo.service;

import com.niukedemo.entity.DiscussPost;
import com.niukedemo.mapper.DiscussPostMapper;
import com.niukedemo.util.CommunityUtil;
import com.niukedemo.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author 刘欢
 * @date 2022年10月06日 15:57
 */
@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * @Description: 分页查询
     * @Param: [userId, offset, limit]
     * @return: java.util.List<com.niukedemo.entity.DiscussPost>
     * @Author: 刘欢
     */
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    /**
     * @Description: 总行数
     * @Param: [userId]
     * @return: int
     * @Author: 刘欢
     */
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    /**
     * @Description: 发布
     * @Param: [discussPost]
     * @return: int
     * @Author: 刘欢
     */
    public int addDiscussPost(DiscussPost discussPost) {
        if (discussPost == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义html标签
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        //过滤敏感词
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));
        return discussPostMapper.insertDiscussPost(discussPost);
    }

    /**
     * @Description: 查询帖子信息
     * @Param: [id]
     * @return: com.niukedemo.entity.DiscussPost
     * @Author: 刘欢
     */
    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    /**
     * @Description: 修改类型 ，置顶
     * @Param: [id, type]
     * @return: int
     * @Author: 刘欢
     */
    public int updateType(int id, int type) {
        return discussPostMapper.updateType(id, type);
    }

    /**
     * @Description: 修改状态，加精
     * @Param: [id, status]
     * @return: int
     * @Author: 刘欢
     */
    public int updateStatus(int id, int status) {
        return discussPostMapper.updateStatus(id, status);
    }
}
