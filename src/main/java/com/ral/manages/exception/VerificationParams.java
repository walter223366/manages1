package com.ral.manages.exception;

import com.ral.manages.entity.manage.Account;
import com.ral.manages.entity.manage.KongFu;
import com.ral.manages.entity.manage.School;
import com.ral.manages.util.StringUtil;

/**
 *   <p>功能描述：参数校验类</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
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

    /**武学管理参数校验*/
    public static String verificationKongFu(KongFu kongFu){
        if(StringUtil.isNull(kongFu.getName()) || StringUtil.isNull(Integer.toString(kongFu.getType()))){
            return "功夫名称或功夫类型不能为空";
        }
        return null;
    }
}
