package com.ral.manages.mapper.app;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IBaseInfoMapper {

    /*查询*/
    List<Map<String,Object>> getBaseInfo(@Param("userId") String userId);

}
