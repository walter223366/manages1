package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.Move;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IMoveMapper {

    /*查询*/
    List<Map<String,Object>> selectMovePagingQuery(Map<String,Object> map);
    int selectMoveToExist(@Param("move")Move move);
    int selectMoveToName(@Param("move")Move move);
    Map<String,Object> selectMoveName(@Param("moveId")String moveId);
    /*新增*/
    void insertMove(@Param("move")Move move);
    /*修改*/
    void updateMove(@Param("move")Move move);
    /*删除*/
    void deleteMove(@Param("move")Move move);
}
