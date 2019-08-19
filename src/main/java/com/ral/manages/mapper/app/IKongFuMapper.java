package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.KongFu;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IKongFuMapper {

    /*查询*/
    List<Map<String,Object>> kongFuPagingQuery(Map<String,Object> map);
    int kongFuIsExist(@Param("kongFu")KongFu kongFu);
    Map<String,Object> kongFuEditQuery(@Param("kongFu") KongFu kongFu);
    Map<String,Object> kongFuIdQuery(@Param("kongFu") KongFu kongFu);
    Map<String,Object> kongFuQueryName(@Param("kongFuId") String kongFuId);
    List<Map<String,Object>> kongFuQueryMarquee();
    List<Map<String,Object>> kongFuQueryMarqueeName(Map<String,Object> map);
    /*新增*/
    void kongFuInsert(@Param("kongFu") KongFu kongFu);
    /*修改*/
    void kongFuUpdate(@Param("kongFu") KongFu kongFu);
    /*删除*/
    void kongFuDelete(@Param("kongFu") KongFu kongFu);
    void kongFuBatchDelete(Map<String,Object> map);
}
