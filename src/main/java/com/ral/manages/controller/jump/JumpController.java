package com.ral.manages.controller.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *   <p>功能描述：页面跳转</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
@Controller
@RequestMapping("/system")
public class JumpController {

    @RequestMapping(value="/{name}")
    public String user(@PathVariable String name){ return name; }

    @RequestMapping("/404")
    public String jumpError(){ return "error/404.html"; }

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
