package com.ral.manages.exception;

import com.ral.manages.entity.manage.Account;
import com.ral.manages.entity.manage.School;
import com.ral.manages.util.StringUtil;

public class VerificationParams {

    /**账号管理参数校验*/
    public static String verificationAccount(Account account){
        if(StringUtil.isNull(account.getAccount())){
            return "账号名称不能为空";
        }
        return null;
    }

    /**门派管理参数校验*/
    public static String verificationSchool(School school){
        if(StringUtil.isNull(school.getName())){
            return "门派名称不能为空";
        }
        return null;
    }
}
