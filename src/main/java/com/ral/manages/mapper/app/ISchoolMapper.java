package com.ral.manages.mapper.app;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ISchoolMapper {

    /*查询*/
    List<Map<String,Object>> schoolPagingQuery(Map<String,Object> map);
    int schoolIsExist(Map<String,Object> map);
    Map<String,Object> schoolEditQuery(Map<String,Object> map);
    Map<String,Object> schoolIdQuery(Map<String,Object> map);
    List<Map<String,Object>> schoolQueryMarquee();
    Map<String,Object> schoolQueryName(@Param("schoolId") String schoolId);
    /*新增*/
    void schoolInsert(Map<String,Object> map);
    /*修改*/
    void schoolUpdate(Map<String,Object> map);
    /*删除*/
    void schoolDelete(Map<String,Object> map);
}
