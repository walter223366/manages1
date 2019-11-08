package com.ral.manages.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringUtil {

    //获取UUID
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }

    //校验字符串
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

    //去掉空格
    public static Object notSpace(Object value){
        value = value.toString().replace(" ","");
        return value;
    }

    //分割时间
    public static Map<String,Object> segTime(String str){
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtil.isNull(str)){
            return map;
        }
        String[] strs = str.split(" - ");
        if(strs.length >= 2){
            map.put("startTime",strs[0]);
            map.put("endTime",strs[1]);
        }else{
            map.put("startTime",strs[0]);
            map.put("endTime","");
        }
        return map;
    }
}
