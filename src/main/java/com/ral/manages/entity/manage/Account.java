package com.ral.manages.entity.manage;

import lombok.Data;

/**
 * 用户表 biz_user
 */
@Data
public class Account {

    private String id;
    private String account;//账号
    private String password;//密码
    private int source;//来源
    private long tellphone;//电话
    private String lrrq;//创建日期
    private int cancellation;//注销标志

}
