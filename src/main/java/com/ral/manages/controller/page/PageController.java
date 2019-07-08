package com.ral.manages.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class PageController {

    @RequestMapping("/account")
    public String accountPage(){
        return "/system/account.html";
    }
}
