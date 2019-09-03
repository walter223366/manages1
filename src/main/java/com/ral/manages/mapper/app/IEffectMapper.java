package com.ral.manages.mapper.app;

import java.util.List;
import java.util.Map;

public interface IEffectMapper {

    /*查询*/
    List<Map<String,Object>> effectPagingQuery(Map<String,Object> map);
    int effectIsExist(Map<String,Object> map);
    Map<String,Object> effectEditQuery(Map<String,Object> map);
    Map<String,Object> effectIdQuery(Map<String,Object> map);
    List<Map<String,Object>> effectQueryMarquee();
    List<Map<String,Object>> effectQueryMarqueeName(Map<String,Object> map);
    /*新增*/
    void effectInsert(Map<String,Object> map);
    /*修改*/
    void effectUpdate(Map<String,Object> map);
    /*删除*/
    void effectDelete(Map<String,Object> map);
}
