package com.ral.manages.mapper.app;

import java.util.List;
import java.util.Map;

public interface ISchoolMapper {

    /*查询*/
    List<Map<String,Object>> schoolPagingQuery(Map<String,Object> map);
    int schoolIsExist(Map<String,Object> map);
    Map<String,Object> schoolEditQuery(Map<String,Object> map);
    Map<String,Object> schoolIdQuery(Map<String,Object> map);
    /*新增*/
    void schoolInsert(Map<String,Object> map);
    /*修改*/
    void schoolUpdate(Map<String,Object> map);
    /*删除*/
    void schoolDelete(Map<String,Object> map);
}
