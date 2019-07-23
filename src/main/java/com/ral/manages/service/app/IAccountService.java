package com.ral.manages.service.app;

import com.ral.manages.entity.app.Account;
import com.ral.manages.commom.response.GeneralResponse;
import java.util.Map;

public interface IAccountService {

    /*查询*/
    GeneralResponse accountPagingQuery(Map<String,Object> map);
    /*新增*/
    GeneralResponse accountAdd(Account account);
    /*修改*/
    GeneralResponse accountUpdate(Account account);
    /*删除*/
    GeneralResponse accountDelete(Account account);

}
