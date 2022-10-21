package com.niukedemo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 刘欢
 * @date 2022年09月30日 23:03
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private  String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;
}
