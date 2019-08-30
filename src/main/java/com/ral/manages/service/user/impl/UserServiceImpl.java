package com.ral.manages.service.user.impl;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.entity.Result;
import com.ral.manages.entity.user.User;
import com.ral.manages.service.user.IUserService;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USERNAME="admin";
    private static final String PASSWORD="123456";

    /**
     * 登陆
     * @param user user
     * @return GeneralResponse
     */
    @Override
    public Result landingInfo(User user) {
        if(StringUtil.isNull(user.getUsername()) || StringUtil.isNull(user.getPassword())){
            return Result.fail("传入用户名或密码为空");
        }
        if(USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())){
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }else{
            return Result.fail("用户名或密码错误");
        }
    }
}
