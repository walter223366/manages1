package com.ral.manages.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetUtil {
    /**
     * List集合，字符串value为null转为""
     */
    public static List<Map<String, Object>> clearValueNullToList(List<Map<String, Object>> list){
        if(null == list || list.size() <= 0){
            return null;
        }
        for(Map<String, Object> map:list){
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

    /**
     * 取map集合key null为""
     * @param map
     * @param key
     * @return
     */
    public final static String toMapValueString(Map<?,?> map, String key){
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

    public final static int toMapValueInt(Map<?,?> map,String key){
        int value = 0;
        if (null == map) {
            return value;
        } else {
            Object obj = map.get(key);
            if (null == obj) {
                return value;
            } else {
                if (null != obj) {
                    value = Integer.valueOf((Integer) obj);
                }
                return value;
            }
        }
    }
}
