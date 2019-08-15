package com.ral.manages.service.user.impl;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.response.GeneralResponse;
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
    public GeneralResponse landingInfo(User user) {
        if(StringUtil.isNull(user.getUsername()) || StringUtil.isNull(user.getPassword())){
            return GeneralResponse.fail("传入用户名或密码为空");
        }
        if(USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())){
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }else{
            return GeneralResponse.fail("用户名或密码错误");
        }
    }
}
