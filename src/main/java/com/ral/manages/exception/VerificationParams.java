package com.ral.manages.exception;

import com.ral.manages.entity.manage.Account;
import com.ral.manages.util.StringUtil;

public class VerificationParams {

    /**账号管理参数校验*/
    public static String verification_Account(Account account){
        if(StringUtil.isNull(account.getAccount()) || StringUtil.isNull(Long.toString(account.getTellphone()))
                || StringUtil.isNull(account.getPassword()) || StringUtil.isNull(Integer.toString(account.getSource()))){
            return "必填项目不能为空";
        }
        return null;
    }
}
