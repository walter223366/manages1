package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.KongFu;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IKongFuMapper {

    /*查询*/
    List<Map<String,Object>> kongFuPagingQuery(Map<String,Object> map);
    int kongFuIsExist(@Param("kongFu")KongFu kongFu);
    List<Map<String,Object>> kongFuToNameId();
    Map<String,Object> kongFuEditQuery(@Param("kongFu") KongFu kongFu);
    Map<String,Object> kongFuQueryName(@Param("kongFuId") String kongFuId);
    /*新增*/
    void kongFuInsert(@Param("kongFu") KongFu kongFu);
    /*修改*/
    void kongFuUpdate(@Param("kongFu") KongFu kongFu);
    /*删除*/
    void kongFuDelete(@Param("kongFu") KongFu kongFu);
}
