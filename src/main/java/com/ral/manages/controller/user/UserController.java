package com.ral.manages.controller.user;

import com.ral.manages.entity.Result;
import com.ral.manages.entity.user.User;
import com.ral.manages.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService iUserService;

    /**
     * 登陆
     * @param user user
     * @return Object
     */
    @RequestMapping("/landing")
    public Object landingInfo(@RequestBody User user){
        LOG.info("请求参数:" + user);
        Result generalResponse = new Result();
        try{
            generalResponse = iUserService.landingInfo(user);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}

