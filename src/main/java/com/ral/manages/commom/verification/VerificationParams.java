package com.ral.manages.commom.verification;

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
            return "账号名称不能为空";
        }
        return null;
    }

    /**门派管理参数校验*/
    public static String verificationSchool(School school){
        if(StringUtil.isNull(school.getName())){
            return "门派名称不能为空";
        }
        return null;
    }

    /**武学管理参数校验*/
    public static String verificationKongFu(KongFu kongFu){
        if(StringUtil.isNull(kongFu.getName()) || StringUtil.isNull(Integer.toString(kongFu.getType()))){
            return "功夫名称或功夫类型不能为空";
        }
        return null;
    }

    /**效果管理参数校验*/
    public static String verificationEffect(Effect effect){
        if(StringUtil.isNull(Integer.toString(effect.getTarget()))){
            return "效果执行目标不能为空";
        }
        return null;
    }

    /**招式管理参数校验*/
    public static String verificationZhaoShi(Move zhaoShi){
        if(StringUtil.isNull(zhaoShi.getName())){
            return "招式名称不能为空";
        }
        if(StringUtil.isNull(zhaoShi.getMP_cost())){
            return "内力花费不能为空";
        }
        if(StringUtil.isNull(zhaoShi.getKongfu_id())){
            return "武学选项不能为空";
        }
        return null;
    }

    /**物品管理参数校验*/
    public static String verificationArticle(Article article){
        if(StringUtil.isNull(article.getName())){
            return "物品名称不能为空";
        }
        if(StringUtil.isNull(article.getImg())){
            return "物品图片不能为空";
        }
       return null;
    }
}
