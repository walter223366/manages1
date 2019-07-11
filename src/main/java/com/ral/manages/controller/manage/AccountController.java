package com.ral.manages.controller.manage;

import com.ral.manages.entity.manage.Account;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.service.manage.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private IAccountService iAccountService;

    /**分页查询*/
    @RequestMapping("/toAccountPage")
    public Object toAccountPage(@RequestBody Map<String,Object> map){
        LOG.info("请求参数：" + map);
        System.out.println(map);
        try{
            return iAccountService.toPagingQueryAtAccount(map);
        }catch (Exception e){
            return GeneralResponse.error("系统错误"+e.getMessage());
        }
    }

    /**新增*/
    @RequestMapping("toAccountAdd")
    public Object toAccountAdd(Account account){
        try{
            GeneralResponse generalResponse = iAccountService.toAddAtAccount(account);
            System.out.println(generalResponse);
            return generalResponse;
        }catch (Exception e){
            return GeneralResponse.error("系统错误"+e.getMessage());
        }
    }
}
