package com.ral.manages.mapper.app;

import java.util.List;
import java.util.Map;

public interface IBaseNPCMapper {

    /*查询*/
    List<Map<String,Object>> baseNpcPagingQuery(Map<String,Object> map);
    int baseNpcIsExist(Map<String,Object> map);
    Map<String,Object> baseNpcEditQuery(Map<String,Object> map);
    Map<String,Object> baseNpcIdQuery(Map<String,Object> map);
    /*新增*/
    void baseNPCInsert(Map<String,Object> map);
    /*修改*/
    void baseNPCUpdate(Map<String,Object> map);
    /*删除*/
    void baseNPCDelete(Map<String,Object> map);
}
