package com.ral.manages.service.user.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.user.User;
import com.ral.manages.service.user.IUserService;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USERNAME="admin";
    private static final String PASSWORD="123456";

    /*登陆*/
    @Override
    public void landingInfo(User user) {
        if(StringUtil.isNull(user.getUsername()) || StringUtil.isNull(user.getPassword())){
            throw new BizException("传入用户名或密码为空");
        }
        if(USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())){

        }else{
            throw new BizException("用户名或密码错误");
        }
    }
}
