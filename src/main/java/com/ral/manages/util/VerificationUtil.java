package com.ral.manages.util;

import com.ral.manages.comms.exception.BizException;
import java.util.Map;

/**
 *   <p>功能描述：参数校验类</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
public class VerificationUtil {

    /**账号管理参数校验*/
    public static void verificationAccount(Map<String,Object> map){
        String account = MapUtil.getString(map,"account");
        String source = MapUtil.getString(map,"source");
        if(StringUtil.isNull(account)){
            throw new BizException("传入账号ID为空") ;
        }
        if(!source.equals("0") && !source.equals("1")){
            throw new BizException("传入账号来源标志错误");
        }
    }


    /**门派管理参数校验*/
    public static void verificationSchool(Map<String,Object> map){
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
           throw new BizException("传入门派名称为空");
        }
    }


    /**武学管理参数校验*/
    public static void verificationKongFu(Map<String,Object> map){
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入功夫名称为空");
        }
        String type = MapUtil.getString(map,"type");
        if(StringUtil.isNull(type)){
            throw new BizException("传入功夫类型为空");
        }
    }


    /**效果管理参数校验*/
    public static void verificationEffect(Map<String,Object> map){
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入效果名称为空");
        }
        String target = MapUtil.getString(map,"target");
        if(!target.equals("0") && !target.equals("1")){
            throw new BizException("传入效果执行目标错误");
        }
    }


    /**招式管理参数校验*/
    public static void verificationMove(Map<String,Object> map){
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入招式名称为空");
        }
        String cost = MapUtil.getString(map,"MP_cost");
        if(StringUtil.isNull(cost)){
            throw new BizException("传入内力花费为空");
        }
        String kongfu_id = MapUtil.getString(map,"kongfu_id");
        if(StringUtil.isNull(kongfu_id)){
            throw new BizException("传入武学选项为空");
        }
        String zhaoshi_effect = MapUtil.getString(map,"zhaoshi_effect");
        if(StringUtil.isNull(zhaoshi_effect)){
            throw new BizException("传入效果选项为空");
        }
    }


    /**物品管理参数校验*/
    public static void verificationArticle(Map<String,Object> map){
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入物品名称为空");
        }
        String img = MapUtil.getString(map,"img");
        if(StringUtil.isNull(img)){
            throw new BizException("传入物品图片为空");
        }
    }


    /*人物管理参数校验*/
    public static void verificationBaseNpc(Map<String,Object> map){
        String nickname = MapUtil.getString(map,"nickname");
        if(StringUtil.isNull(nickname)){
            throw new BizException("传入人物名称为空");
        }
        String school_id = MapUtil.getString(map,"school_id");
        if(StringUtil.isNull(school_id)){
            throw new BizException("传入所属门派为空");
        }
    }
}
