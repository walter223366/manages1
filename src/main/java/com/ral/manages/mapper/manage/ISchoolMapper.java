package com.ral.manages.mapper.manage;

import com.ral.manages.entity.app.School;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ISchoolMapper {

    /*查询*/
    List<Map<String,Object>> selectSchoolPagingQuery(Map<String,Object> map);
    int selectSchoolToExist(@Param("school") School school);
    /*新增*/
    void insertSchool(@Param("school") School school);
    /*修改*/
    void updateSchool(@Param("school") School school);
    /*删除*/
    void deleteSchool(@Param("school") School school);
}
