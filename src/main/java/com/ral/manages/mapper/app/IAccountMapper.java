package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.Account;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IAccountMapper {

    /*查询*/
    List<Map<String,Object>> selectAccountPagingQuery(Map<String,Object> map);
    int selectAccountToExist(@Param("account") Account account);
    /*新增*/
    void insertAccount(@Param("account") Account account);
    /*修改*/
    void updateAccount(@Param("account") Account account);
    /*删除*/
    void deleteAccount(@Param("account") Account account);
}
