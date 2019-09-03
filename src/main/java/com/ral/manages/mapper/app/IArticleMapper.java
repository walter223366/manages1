package com.ral.manages.mapper.app;

import java.util.List;
import java.util.Map;

public interface IArticleMapper {

    /*查询*/
    List<Map<String,Object>> articlePagingQuery(Map<String,Object> map);
    int articleIsExist(Map<String,Object> map);
    Map<String,Object> articleEditQuery(Map<String,Object> map);
    Map<String,Object> articleIdQuery(Map<String,Object> map);
    /*新增*/
    void articleInsert(Map<String,Object> map);
    /*修改*/
    void articleUpdate(Map<String,Object> map);
    /*删除*/
    void articleDelete(Map<String,Object> map);
}
