package com.ral.manages.commom.page;

import com.ral.manages.util.SetUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageBean {

    private final static int PAGENUM = 1;
    private final static int PAGESIZE = 10;

    /**页数*/
    public static int pageNum(Map<String,Object> map){
        try{
            int pageNum = SetUtil.toMapValueInt(map,"pageNum");
            pageNum = (pageNum==0?PAGENUM:pageNum);
            return pageNum;
        }catch (Exception e){
            return PAGENUM;
        }
    }
    /**每页条数*/
    public static int pageSize(Map<String,Object> map){
        try{
            int pageSize = SetUtil.toMapValueInt(map,"pageSize");
            pageSize = (pageSize==0?PAGESIZE:pageSize);
            return pageSize;
        }catch (Exception e){
            return PAGESIZE;
        }
    }
    /**分页返回*/
    public static Map<String,Object> resultPage(long total, List<Map<String,Object>> datas){
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("datas",datas);
        result.put("total",total);
        return result;
    }
}
