package com.ral.manages.util;

import java.util.*;

public class SetUtil {

    public static boolean isMapNull(Map<String,Object> map){
        if(null == map || map.isEmpty()){
            return true;
        }else{
            return false;
        }
    }


    /**
     * List集合，字符串value为null转为""
     */
    public static List<Map<String,Object>> clearValueNullToList(List<Map<String,Object>> list){
        if(null == list || list.size() <= 0){
            return null;
        }
        for(Map<String,Object> map:list){
            List<String> keyList = new ArrayList<String>();
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                Object value = map.get(key);
                if(null == value){
                    keyList.add(key);
                }
            }
            for (String key : keyList) {
                map.put(key, "");
            }
        }
        return list;
    }

    public static Map<String,Object> clearValueNullToMap(Map<String,Object> map){
        if(null == map || map.size() <= 0){
            return null;
        }
        List<String> keyList = new ArrayList<String>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            Object value = map.get(key);
            if(null == value){
                keyList.add(key);
            }
        }
        for (String key : keyList) {
            map.put(key, "");
        }
        return map;
    }


    /**
     * 获取map集合value
     * @param map
     * @param key
     * @return String
     */
    public static String toMapValueString(Map<String,Object> map,String key){
        String value = "";
        if (null == map) {
            return value;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return value;
        }else{
            value = obj.toString();
        }
        return value;
    }

    /**
     * 获取map集合value
     * @param map
     * @param key
     * @return int
     */
    public static int toMapValueInt(Map<String,Object> map,String key){
        int value = 0;
        if (null == map) {
            return value;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return value;
        }else{
            value = Integer.parseInt(obj.toString());
        }
        return value;
    }
}
