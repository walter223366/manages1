package com.ral.manages.mapper.manage;

import com.ral.manages.entity.manage.Account;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface IAccountMapper {

    //查询
    List<Account> selectAccount(@Param("account") Account account);
    //新增
    void insertAccount(@Param("account") Account account);
    //修改
    void updateAccount(@Param("account") Account account);
    //删除
    void deleteAccount(@Param("account") Account account);
}
