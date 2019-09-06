package com.ral.manages.util;

import com.ral.manages.comms.exception.BizException;
import java.util.Map;

public class MapUtil {

    //Map集合value为String类型
    public static String getString(Map<String,Object> map, String key) {
        String value = "";
        if (null == map) {
            return value;
        }
        try {
            value = map.get(key) == null ? "" : map.get(key).toString();
        } catch (Exception e) {
            throw new BizException("类型错误");
        }
        return value;
    }

    //Map集合value为int类型
    public static int getInt(Map<String,Object> map, String key) {
        String value = "";
        int result = 0;
        try {
            value = map.get(key) == null ? "0" : map.get(key).toString();
            result = Integer.parseInt(value);
        } catch (Exception e) {
            throw new BizException("类型错误");
        }
        return result;
    }
}
