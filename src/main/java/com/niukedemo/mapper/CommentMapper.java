package com.niukedemo.mapper;

import com.niukedemo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * @Description:分页查询评论
     * @Param: [entityType, entityId, offset, limit]
     * @return: java.util.List<com.niukedemo.entity.Comment>
     * @Author: 刘欢
     */
    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * @Description: 评论总数
     * @Param: [entityType, entityId]
     * @return: int
     * @Author: 刘欢
     */
    int selectCountByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    /**
     * @Description: 添加一条评论
     * @Param: [comment]
     * @return: int
     * @Author: 刘欢
     */
    int insertComment(Comment comment);
    Comment selectCommentById(@Param("id")int id);

}
