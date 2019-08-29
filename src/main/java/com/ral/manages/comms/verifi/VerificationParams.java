package com.ral.manages.comms.verifi;

import com.ral.manages.entity.app.*;
import com.ral.manages.util.StringUtil;

/**
 *   <p>功能描述：参数校验类</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
public class VerificationParams {

    /**账号管理参数校验*/
    public static String verificationAccount(Account account){
        if(StringUtil.isNull(account.getAccount())){
            return "传入账号名称为空";
        }
        if(account.getSource()!=0 && account.getSource()!=1){
            return "传入账号来源标志错误";
        }
        return null;
    }


    /**门派管理参数校验*/
    public static String verificationSchool(School school){
        if(StringUtil.isNull(school.getName())){
            return "传入门派名称为空";
        }
        return null;
    }


    /**武学管理参数校验*/
    public static String verificationKongFu(KongFu kongFu){
        if(StringUtil.isNull(kongFu.getName())){
            return "传入功夫名称为空";
        }
        if(StringUtil.isNull(Integer.toString(kongFu.getType()))){
            return "传入功夫类型为空";
        }
        return null;
    }


    /**效果管理参数校验*/
    public static String verificationEffect(Effect effect){
        if(StringUtil.isNull(effect.getName())){
            return "传入效果名称为空";
        }
        if(effect.getTarget()!=0 && effect.getTarget()!=1){
            return "传入效果执行目标错误";
        }
        return null;
    }


    /**招式管理参数校验*/
    public static String verificationMove(Move move){
        if(StringUtil.isNull(move.getName())){
            return "传入招式名称为空";
        }
        if(StringUtil.isNull(move.getMP_cost())){
            return "传入内力花费为空";
        }
        if(StringUtil.isNull(move.getKongfu_id())){
            return "传入武学选项为空";
        }
        if(StringUtil.isNull(move.getZhaoshi_effect())){
            return "传入效果选项为空";
        }
        return null;
    }


    /**物品管理参数校验*/
    public static String verificationArticle(Article article){
        if(StringUtil.isNull(article.getName())){
            return "传入物品名称为空";
        }
        if(StringUtil.isNull(article.getImg())){
            return "传入物品图片为空";
        }
       return null;
    }
}
