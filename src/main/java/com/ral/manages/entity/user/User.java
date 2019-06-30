package com.ral.manages.entity.user;

import lombok.Data;

/**
 * 用户表
 */
@Data
public class User {

    private String id;
    private String account;//账号
    private String pass;//密码
    private int source;//来源
    private int tellphone;//电话
    private String lrrq;//创建日期
    private int cancellation;//注销标志

}
