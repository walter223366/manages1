package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.School;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ISchoolMapper {

    /*查询*/
    List<Map<String,Object>> schoolPagingQuery(Map<String,Object> map);
    int schoolIsExist(@Param("school") School school);
    Map<String,Object> schoolEditQuery(@Param("school") School school);
    Map<String,Object> schoolIdQuery(@Param("school") School school);
    /*新增*/
    void schoolInsert(@Param("school") School school);
    /*修改*/
    void schoolUpdate(@Param("school") School school);
    /*删除*/
    void schoolDelete(@Param("school") School school);
    void schoolBatchDelete(Map<String,Object> map);
}
