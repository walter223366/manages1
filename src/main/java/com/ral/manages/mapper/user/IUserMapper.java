package com.ral.manages.mapper.user;

import com.ral.manages.entity.user.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 biz_user
 * @author double
 */
public interface IUserMapper {

    //用户查询
    User selectUserInfo(@Param("user") User user);
    int selectUserExist(@Param("user") User user);
    User selectUserExistToAccount(@Param("user") User user);

    //用户新增
    void insertUserInfo(@Param("user") User user);

    //用户修改
    int updateUserInfo(@Param("user") User user);

    //用户注销
    int updateUserInfoToCancellation(@Param("user") User user);

}
