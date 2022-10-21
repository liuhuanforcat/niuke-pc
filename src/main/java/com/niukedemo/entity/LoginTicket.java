package com.niukedemo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 刘欢
 * @date 2022年10月11日 18:59
 */
@Data
public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private int status;
    private Date expired;
    private Date createTime;
}
