package com.ral.manages.mapper.app;

import java.util.List;
import java.util.Map;

public interface IGoodsMapper {

    /*查询*/
    List<Map<String,Object>> goodsPagingQuery(Map<String,Object> map);
    int goodsIsExist(Map<String,Object> map);
    Map<String,Object> goodsEditQuery(Map<String,Object> map);
    Map<String,Object> goodsIdQuery(Map<String,Object> map);
    /*新增*/
    void goodsInsert(Map<String,Object> map);
    /*修改*/
    void goodsUpdate(Map<String,Object> map);
    /*删除*/
    void goodsDelete(Map<String,Object> map);
}
