package com.ral.manages.service.user;

import com.ral.manages.entity.Result;
import com.ral.manages.entity.user.User;

public interface IUserService {

    /*登录*/
    Result landingInfo(User user);
}
