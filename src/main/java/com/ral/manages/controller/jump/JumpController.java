package com.ral.manages.controller.jump;

import com.ral.manages.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *   页面跳转
 *
 *   @author Double
 *   @since  2019-07-18
 */
@Controller
public class JumpController {

    @RequestMapping(value="/{name}")
    public String user(@PathVariable String name){ return name; }

    @RequestMapping("/system/main")
    public String jumpMain(HttpServletRequest request){
        String username = request.getParameter("username");
        if(StringUtil.isNull(username)){
            return "error/404.html";
        }
        return "main.html";
    }

    @RequestMapping("/404")
    public String jump404(){ return "error/404.html"; }

    @RequestMapping("/errors")
    public String jumpError(){ return "error/errors.html"; }

    @RequestMapping("/system/account")
    public String jumpAccount(){ return "app/account.html"; }

    @RequestMapping("/system/school")
    public String jumpSchool(){ return "app/school.html"; }

    @RequestMapping("/system/effect")
    public String jumpEffect(){ return "app/effect.html"; }

    @RequestMapping("/system/move")
    public String jumpZhaoShi(){ return "app/move.html"; }

    @RequestMapping("/system/kongFu")
    public String jumpKongFu(){ return "app/kongfu.html"; }

    @RequestMapping("/system/article")
    public String jumpCharacter(){ return "app/article.html"; }

}
