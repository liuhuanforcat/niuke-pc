package com.niukedemo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 刘欢
 * @date 2022年10月01日 20:22
 */
@Data
public class DiscussPost {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private Date createTime;
    private int commentCount;
    private double score;
}
