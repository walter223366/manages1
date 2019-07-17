package com.ral.manages.service.manage;

import com.ral.manages.entity.manage.Account;
import com.ral.manages.exception.GeneralResponse;
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
