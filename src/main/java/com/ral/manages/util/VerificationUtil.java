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
            throw new BizException("传入武学名称为空");
        }
        String type = MapUtil.getString(map,"type");
        if(StringUtil.isNull(type)){
            throw new BizException("传入武学类型为空");
        }
        String lv = MapUtil.getString(map,"LV");
        if(StringUtil.isNull(lv)){
            throw new BizException("传入武学等级为空");
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
    }


    /**物品管理参数校验*/
    public static void verificationGoods(Map<String,Object> map){
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入物品名称为空");
        }
        String type = MapUtil.getString(map,"type");
        if(StringUtil.isNull(type)){
            throw new BizException("传入物品类型为空");
        }
    }


    /*人物管理参数校验*/
    public static void verificationBase(Map<String,Object> map){
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
