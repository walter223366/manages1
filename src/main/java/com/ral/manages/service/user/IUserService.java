package com.ral.manages.service.user;

import com.ral.manages.entity.user.User;

/**
 * 用户表
 * @author double
 */
public interface IUserService {

    //用户登录
    void loadUserInfo(User user);

    //用户新增
    void newUserInfo(User user);

    //用户修改
    void updateUserInfo(User user);

    //用户注销
    void updataUserToCancet(User user);

}
