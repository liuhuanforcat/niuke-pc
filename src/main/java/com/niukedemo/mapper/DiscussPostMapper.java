package com.niukedemo.mapper;

import com.niukedemo.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    /**
     * @Description: 分页查询帖子
     * @Param: [userId, offset, limit]
     * @return: java.util.List<com.niukedemo.entity.DiscussPost>
     * @Author: 刘欢
     */
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * @Description: 查询帖子数
     * @Param: [userId]
     * @return: int
     * @Author: 刘欢
     * 注意:Param用于给参数取别名，如果参数列表只有一个 参数。又是在if中使用，必须加别名
     */
//    @Select("select count(*) from discuss_post where user_Id!=2 ")
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * @Description: 发布帖子
     * @Param: [discussPost]
     * @return: int
     * @Author: 刘欢
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     * @Description:帖子详情，查询帖子的详细信息
     * @Param: [id]
     * @return: com.niukedemo.entity.DiscussPost
     * @Author: 刘欢
     */
    DiscussPost selectDiscussPostById(int id);

    /**
     * @Description:修改评论条数
     * @Param: [id, commentCount]
     * @return: int
     * @Author: 刘欢
     */
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
