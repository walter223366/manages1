package com.ral.manages.util;

import java.util.UUID;

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
    public static boolean isNull(String str){
        if(null == str || str.equals("") || str.equals("null")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 去掉空格
     */
    public static String notSpace(String str){
        str = str.replace(" ","");
        return str;
    }
}
