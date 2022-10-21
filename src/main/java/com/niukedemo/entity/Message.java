package com.niukedemo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 刘欢
 * @date 2022年10月18日 23:33
 */
@Data
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;
}
