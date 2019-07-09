package com.ral.manages.controller.manage;

import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.service.manage.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private IAccountService iAccountService;

    @RequestMapping("/toPagingQuery")
    @ResponseBody
    public GeneralResponse selectAccountToPage(@RequestBody Map<String,Object> map){
        System.out.println(map);
        try{
            GeneralResponse generalResponse = iAccountService.selectAccountToPage(map);
            return generalResponse;
        }catch (Exception e){
            return new GeneralResponse().error(e.getMessage());
        }
    }
}
