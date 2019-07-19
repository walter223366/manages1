package com.ral.manages.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtil {

    /**页数*/
    public static int pageNum(Map<String,Object> map){
        int pageNum = SetUtil.toMapValueInt(map,"pageNum");
        pageNum = (pageNum==0?1:pageNum);
        return pageNum;
    }
    /**每页条数*/
    public static int pageSize(Map<String,Object> map){
        int pageSize = SetUtil.toMapValueInt(map,"pageSize");
        pageSize = (pageSize==0?10:pageSize);
        return pageSize;
    }
    /**分页返回*/
    public static Map<String,Object> resultPage(long total, List<Map<String,Object>> datas){
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("datas",datas);
        result.put("total",total);
        return result;
    }
}
