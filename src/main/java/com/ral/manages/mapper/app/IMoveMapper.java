package com.ral.manages.mapper.app;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IMoveMapper {

    /*查询*/
    List<Map<String,Object>> movePagingQuery(Map<String,Object> map);
    int moveIsExist(Map<String,Object> map);
    Map<String,Object> moveEditQuery(Map<String,Object> map);
    Map<String,Object> moveIdQuery(Map<String,Object> map);
    List<Map<String,Object>> moveQueryMarquee();
    Map<String,Object> moveQueryName(@Param("moveId") String moveId);
    List<Map<String,Object>> moveQueryMarqueeName(Map<String,Object> map);
    /*新增*/
    void moveInsert(Map<String,Object> map);
    /*修改*/
    void moveUpdate(Map<String,Object> map);
    /*删除*/
    void moveDelete(Map<String,Object> map);
}
