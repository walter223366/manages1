package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.Account;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IAccountMapper {

    /*查询*/
    List<Map<String,Object>> accountPagingQuery(Map<String,Object> map);
    int accountIsExist(@Param("account") Account account);
    Map<String,Object> accountEditQuery(@Param("account") Account account);
    Map<String,Object> accountIdQuery(@Param("account") Account account);
    /*新增*/
    void accountInsert(@Param("account") Account account);
    /*修改*/
    void accountUpdate(@Param("account") Account account);
    /*删除*/
    void accountDelete(@Param("account") Account account);
    void accountBatchDelete(Map<String,Object> map);
}
