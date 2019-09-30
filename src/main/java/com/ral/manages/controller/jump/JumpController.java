package com.ral.manages.controller.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *   页面跳转
 *
 *   @author Double
 *   @since  2019-07-18
 */
@Controller
@RequestMapping("/system/")
public class JumpController {

    @RequestMapping("main")
    public String jumpMain(){
        return "main.html";
    }

    @RequestMapping("account")
    public String jumpAccount(){
        return "app/account.html";
    }

    @RequestMapping("school")
    public String jumpSchool(){
        return "app/school.html";
    }

    @RequestMapping("effect")
    public String jumpEffect(){
        return "app/effect/effect.html";
    }

    @RequestMapping("effect_add")
    public String jumpEffectAdd() { return "app/effect/effect-add.html"; }

    @RequestMapping("move")
    public String jumpMove(){
        return "app/move.html";
    }

    @RequestMapping("kongFu")
    public String jumpKongFu(){
        return "app/kongfu.html";
    }

    @RequestMapping("article")
    public String jumpArticle(){
        return "app/article.html";
    }

    @RequestMapping("baseInfo")
    public String jumpBaseInfo(){
        return "app/baseinfo.html";
    }

    @RequestMapping("baseNpc")
    public String jumpBaseNpc(){
        return "app/baseNpc/basenpc.html";
    }

    @RequestMapping("baseNpc_add")
    public String jumpBaseNpcAdd(){ return "app/baseNpc/basenpc-add.html"; }
}
