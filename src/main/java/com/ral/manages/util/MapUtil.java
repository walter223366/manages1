package com.ral.manages.util;

import com.ral.manages.comms.exception.BizException;
import java.util.Map;

public class MapUtil {

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
}
