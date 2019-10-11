package com.ral.manages.util;

import com.ral.manages.comms.exception.BizException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /*校验请求参数json格式*/
    public static Map<String,Object> formatJSON(String str){
        if(StringUtil.isNull(str)){
            throw new BizException("传入参数为空");
        }
        try{
            return JSONObject.fromObject(str);
        }catch (Exception e){
            log.debug("传入JSON格式错误;"+e.getMessage());
            throw new BizException("传入JSON格式错误;"+e.getMessage());
        }
    }

    public static List<Map<String,Object>> formatList(String str){
        if(StringUtil.isNull(str)){
            throw new BizException("传入参数为空");
        }
        try{
            return JSONArray.fromObject(str);
        }catch (Exception e){
            log.debug("传入JSON格式错误;"+e.getMessage());
            throw new BizException("传入JSON格式错误;"+e.getMessage());
        }
    }
}
