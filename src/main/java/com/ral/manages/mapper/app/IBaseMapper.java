package com.ral.manages.mapper.app;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IBaseMapper {

    /*查询*/
    List<Map<String,Object>> basePagingQuery(Map<String,Object> map);
    int baseIsExist(Map<String,Object> map);
    Map<String,Object> baseEditQuery(Map<String,Object> map);
    Map<String,Object> baseIdQuery(Map<String,Object> map);

    /*查询*/
    List<Map<String,Object>> queryBaseInfo(@Param("userId") String userId);
    Map<String,Object> queryAttainmentsInfo(@Param("baseId") String baseId);
    Map<String,Object> queryBattleInfo(@Param("baseId") String baseId);
    Map<String,Object> queryPotentialInfo(@Param("baseId") String baseId);
    List<Map<String,Object>> queryRelationInfo(@Param("baseId") String baseId);


    /*新增*/
    void baseInsert(Map<String,Object> map);//人物
    void baseAttInsert(Map<String,Object> map);//兵器造诣
    void baseExtInsert(Map<String,Object> map);//人物属性
    void basePotInsert(Map<String,Object> map);//人物潜能

    /*修改*/
    void baseUpdate(Map<String,Object> map);//人物
    void baseAttUpdate(Map<String,Object> map);//兵器造诣
    void baseExtUpdate(Map<String,Object> map);//人物属性
    void basePotUpdate(Map<String,Object> map);//人物潜能

    /*删除*/
    void baseDelete(Map<String,Object> map);
}
