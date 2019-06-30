package com.ral.manages.service.user;

import com.ral.manages.entity.user.User;
import com.ral.manages.exception.Result;

/**
 * 用户表
 * @author double
 */
public interface IUserService {

    //用户登录
    Result loadUserInfo(User user);

    //用户新增
    void newUserInfo(User user);

    //用户修改
    Result updateUserInfo(User user);

    //用户注销
    Result updataUserToCancet(User user);

}
