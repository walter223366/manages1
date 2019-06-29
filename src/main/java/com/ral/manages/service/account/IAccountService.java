package com.ral.manages.service.account;

import com.ral.manages.entity.account.AccountEntity;
import com.ral.manages.util.page.PageBean;
import java.util.Map;

public interface IAccountService {

    //登录账号
    Map<String,Object> loadAccountInfo(AccountEntity accountInfo);

    //查询账号
    PageBean<?> pageCheckAccountInfo(Map<String,Object> map);

    //新增账号
    int newAccountInfo(Map<String,Object> map);

    //注销账号
    int logoutAccountInfo(Map<String,Object> map);

    //修改账号
    int modifyAccountInfo(Map<String,Object> map);
}
