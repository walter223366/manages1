package com.ral.manages.service.app;

import com.ral.manages.entity.Result;
import com.ral.manages.entity.app.Account;
import java.util.Map;

public interface IAccountService {

    /*查询*/
    Result accountPagingQuery(Map<String,Object> map);
    Result accountEditQuery(Account account);
    /*新增*/
    Result accountInsert(Account account);
    /*修改*/
    Result accountUpdate(Account account);
    /*删除*/
    Result accountDelete(Account account);
    Result accountBatchDelete(Map<String,Object> map);
    //登陆或注册
    Result accountSignUp(Account account);

}
