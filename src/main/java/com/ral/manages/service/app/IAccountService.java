package com.ral.manages.service.app;

import com.ral.manages.comms.response.GeneralResponse;
import com.ral.manages.entity.app.Account;
import java.util.Map;

public interface IAccountService {

    /*查询*/
    GeneralResponse accountPagingQuery(Map<String,Object> map);
    GeneralResponse accountEditQuery(Account account);
    /*新增*/
    GeneralResponse accountInsert(Account account);
    /*修改*/
    GeneralResponse accountUpdate(Account account);
    /*删除*/
    GeneralResponse accountDelete(Account account);
    GeneralResponse accountBatchDelete(Map<String,Object> map);
    //登陆或注册
    GeneralResponse accountSignUp(Account account);

}
