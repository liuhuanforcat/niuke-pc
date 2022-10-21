package com.niukedemo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 刘欢
 * @date 2022年10月16日 15:52
 */
@Data
public class Comment {
    private int id;
    private int userId;
    private int entityType;
    private int entityId;
    private int targetId;
    private String content;
    private int status;
    private Date createTime;
}
