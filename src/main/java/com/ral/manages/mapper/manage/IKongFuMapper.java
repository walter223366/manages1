package com.ral.manages.mapper.manage;

import com.ral.manages.entity.manage.KongFu;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IKongFuMapper {

    /*查询*/
    List<Map<String,Object>> selectKongFuPagingQuery(Map<String,Object> map);
    int selectKongFuToExist(@Param("kongFu")KongFu kongFu);
    /*新增*/
    void insertKongFu(@Param("kongFu")KongFu kongFu);
    /*修改*/
    void updateKongFu(@Param("kongFu")KongFu kongFu);
    /*删除*/
    void deleteKongFu(@Param("kongFu")KongFu kongFu);
}
