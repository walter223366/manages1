package com.ral.manages.controller.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *   页面跳转
 *
 *   @author Double
 *   @since  2019-07-18
 */
@Controller
@RequestMapping("/system")
public class JumpController {

    @RequestMapping(value="/{name}")
    public String user(@PathVariable String name){ return name; }

    @RequestMapping("/rError")
    public String jumpError(){ return "error/rError.html"; }

    @RequestMapping("/account")
    public String jumpAccount(){ return "app/account.html"; }

    @RequestMapping("/school")
    public String jumpSchool(){ return "app/school.html"; }

    @RequestMapping("/effect")
    public String jumpEffect(){ return "app/effect.html"; }

    @RequestMapping("/move")
    public String jumpZhaoShi(){ return "app/move.html"; }

    @RequestMapping("/kongFu")
    public String jumpKongFu(){ return "app/kongfu.html"; }

    @RequestMapping("/article")
    public String jumpCharacter(){ return "app/article.html"; }

}
