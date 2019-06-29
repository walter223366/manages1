package com.ral.manages.util;

import java.util.Map;

public class ParamsUtil {

    private ParamsUtil(){ }

    public final static  String toMapGetValue(Map<?,?> map,String key){
        String value = "";
        if (null == map) {
            return value;
        } else {
            Object obj = map.get(key);
            if (null == obj) {
                return "";
            } else {
                if (null != obj) {
                    value = obj.toString();
                }
                return value;
            }
        }
    }
}
