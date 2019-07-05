package com.ral.manages.util;

import java.util.UUID;

/**
 * 字符串工具类
 * @author Doublc
 */
public class StringUtil {

    /**
     * 获取UUID
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }

    /**
     * 校验字符串
     */
    public static boolean isNull(String value){
        if(null == value ){
            return true;
        }
        if("".equals(value)){
            return true;
        }
        if("null".equals(value)){
            return true;
        }
        return false;

    }


    /**
     * 去掉空格
     */
    public static String notSpace(String value){
        value = value.replace(" ","");
        return value;
    }
}
