package com.ral.manages.mapper.app;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IBaseInfoMapper {

    /*查询*/
    List<Map<String,Object>> queryBaseInfo(@Param("userId") String userId);
    List<Map<String,Object>> queryAttainmentsInfo(@Param("baseId") String baseId);
    List<Map<String,Object>> queryBattleInfo(@Param("baseId") String baseId);
    List<Map<String,Object>> queryPotentialInfo(@Param("baseId") String baseId);
    List<Map<String,Object>> queryRelationInfo(@Param("baseId") String baseId);
}
