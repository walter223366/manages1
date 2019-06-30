package com.ral.manages.check;

import com.ral.manages.entity.user.User;
import com.ral.manages.exception.BizException;
import com.ral.manages.util.StringUtil;

public class CheckParams {

    /**校验参数*/
    public static void userCheck(User user){
        if(StringUtil.isNull(user.getAccount()) || StringUtil.isNull(user.getPass())){
            throw new BizException("传入账号或密码为空");
        }
    }
}
