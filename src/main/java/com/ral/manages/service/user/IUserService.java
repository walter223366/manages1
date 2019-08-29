package com.ral.manages.service.user;

import com.ral.manages.comms.response.GeneralResponse;
import com.ral.manages.entity.user.User;

public interface IUserService {

    /*登录*/
    GeneralResponse landingInfo(User user);
}
