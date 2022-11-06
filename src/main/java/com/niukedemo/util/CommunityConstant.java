package com.niukedemo.util;

/**
 * @author 刘欢
 * @date 2022年10月09日 22:35
 */

public interface CommunityConstant {
    /**
     * @Description:邮件激活成功
     */
    int ACTIVATION_SUCCESS = 0;
    /**
     * @Description:邮件重复激活
     */
    int ACTIVATION_REPEAT = 1;
    /**
     * @Description:邮件激活失败
     */
    int ACTIVATION_FAILURE = 2;
    /**
     * @Description:默认状态的登录凭证的超时时间,12小时
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    /**
     * @Description:记住状态的登录凭证的超时时间,一百天
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
    /**
     * @Description: 实体类型：帖子
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    int ENTITY_TYPE_POST = 1;
    /**
     * @Description:实体类型：评论
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    int ENTITY_TYPE_COMMENT = 1;
    /**
     * @Description: 实体类型：用户
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    int ENTITY_TYPE_USER = 3;
    /**
     * @Description: 评论
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String TOPIC_COMMENT = "comment";
    /**
     * @Description: 点赞
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String TOPIC_LIKE = "like";
    /**
     * @Description: 关注
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String TOPIC_FOLLOW = "follow";
    /**
     * @Description: 系统用户ID
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    int SYSTEM_USER_ID = 1;
    /**
     * @Description: 主题发帖
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String TOPIC_PUBLISH = "publish";
    /**
     * @Description: 删帖
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String TOPIC_DELETE = "delete";
    /**
     * @Description: 普通用户
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String AUTHORITY_USER = "user";
    /**
     * @Description: 管理员
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String AUTHORITY_ADMIN = "admin";
    /**
     * @Description: 版主
     * @Param:
     * @return:
     * @Author: 刘欢
     */
    String AUTHORITY_MODERATOR = "moderator";
}
