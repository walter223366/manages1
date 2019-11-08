package com.ral.manages.mapper.app;

import java.util.List;
import java.util.Map;

public interface IEquipmentMapper {

    /*查询*/
    List<Map<String,Object>> equipPagingQuery(Map<String,Object> map);
    /*新增*/
    void equipInsert(Map<String,Object> map);
    /*修改*/
    void equipUpdate(Map<String,Object> map);
    /*删除*/
    void equipDelete(Map<String,Object> map);
}
