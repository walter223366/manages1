package com.ral.manages.mapper.manage;

import com.ral.manages.entity.manage.KongFu;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface IKongFuMapper {

    //查询
    List<KongFu> selectKongFu(@Param("kongFu") KongFu kongfu);
    //新增
    void insertKongFu(@Param("kongFu") KongFu kongfu);
    //修改
    void updateKongFu(@Param("kongFu") KongFu kongfu);
    //删除
    void deleteKongFu(@Param("kongFu") KongFu kongfu);
}
