package com.ral.manages.service.manage;

import com.ral.manages.entity.manage.Account;
import com.ral.manages.exception.GeneralResponse;
import java.util.Map;

public interface IAccountService {

    //查询
    GeneralResponse toPagingQueryAtAccount(Map<String,Object> map);

    //新增
    GeneralResponse toAddAtAccount(Account account);

}
