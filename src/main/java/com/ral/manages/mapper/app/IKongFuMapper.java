package com.ral.manages.mapper.app;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IKongFuMapper {

    /*查询*/
    List<Map<String,Object>> kongFuPagingQuery(Map<String,Object> map);
    int kongFuIsExist(Map<String,Object> map);
    Map<String,Object> kongFuEditQuery(Map<String,Object> map);
    Map<String,Object> kongFuIdQuery(Map<String,Object> map);
    Map<String,Object> kongFuQueryName(@Param("kongFuId") String kongFuId);
    List<Map<String,Object>> kongFuQueryMarquee(Map<String,Object> map);
    List<Map<String,Object>> kongFuQueryMarqueeName(Map<String,Object> map);
    /*新增*/
    void kongFuInsert(Map<String,Object> map);
    /*修改*/
    void kongFuUpdate(Map<String,Object> map);
    /*删除*/
    void kongFuDelete(Map<String,Object> map);
}
