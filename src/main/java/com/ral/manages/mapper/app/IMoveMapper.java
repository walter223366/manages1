package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.Move;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IMoveMapper {

    /*查询*/
    List<Map<String,Object>> movePagingQuery(Map<String,Object> map);
    int moveIsExist(@Param("move") Move move);
    int moveIsName(@Param("move") Move move);
    Map<String,Object> moveEditQuery(@Param("move") Move move);
    Map<String,Object> moveQueryName(@Param("moveId") String moveId);
    /*新增*/
    void moveInsert(@Param("move") Move move);
    /*修改*/
    void moveUpdate(@Param("move") Move move);
    /*删除*/
    void moveDelete(@Param("move") Move move);
}
