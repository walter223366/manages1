package com.ral.manages.controller.user;

import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService iUserService;

    /**分页查询*/
    @RequestMapping("/pagingQuery")
    public Object accountPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

}

