package com.niukedemo.util;


/**
 * 说明: 生成redis数据库的键
 *
 * @author 刘欢
 * @date 2022年10月23日 11:05
 */

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static  final String PREFIX_USER_LIKE="like:user";

    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE+userId;
    }

}
