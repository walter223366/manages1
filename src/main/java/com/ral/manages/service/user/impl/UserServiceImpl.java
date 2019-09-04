package com.ral.manages.service.user.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.service.user.IUserService;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("user")
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USERNAME="admin";
    private static final String PASSWORD="123456";

    /*登陆*/
    @Override
    public void landingInfo(Map<String,Object> map) {
        String username = MapUtil.getString(map,"username");
        String password = MapUtil.getString(map,"password");
        if(StringUtil.isNull(username) || StringUtil.isNull(password)){
            throw new BizException("用户名或密码为空");
        }
        if(USERNAME.equals(username) && PASSWORD.equals(password)){

        }else{
            throw new BizException("用户名或密码错误");
        }
    }
}
