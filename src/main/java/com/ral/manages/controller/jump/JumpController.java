package com.ral.manages.controller.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class JumpController {

    @RequestMapping(value="/{name}")
    public String user(@PathVariable String name){ return name; }

    @RequestMapping("/404")
    public String jumpError(){ return "/error/404.html"; }

    @RequestMapping("/account")
    public String jumpAccount(){ return "/system/account.html"; }

    @RequestMapping("/account/add")
    public String jumpAccountAdd(){ return "/system/account-add.html"; }

    @RequestMapping("/account/update")
    public String jumpAccountUpdate(){ return "/system/account-update.html"; }

    @RequestMapping("/school")
    public String jumpSchool(){ return "/system/school.html"; }

    @RequestMapping("/school/add")
    public String jumpSchoolAdd(){ return "/system/school-add.html"; }
}
