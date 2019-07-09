package com.ral.manages.controller.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class JumpController {

    @RequestMapping(value="/{name}")
    public String user(@PathVariable String name){
        return name;
    }

    @RequestMapping("/account")
    public String jumpAccount(){
        return "/system/account.html";
    }

    @RequestMapping("/school")
    public String jumpSchool(){ return "/system/school.html"; }
}
