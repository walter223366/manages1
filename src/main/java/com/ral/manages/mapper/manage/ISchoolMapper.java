package com.ral.manages.mapper.manage;

import com.ral.manages.entity.manage.School;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ISchoolMapper {

    List<School> selectPageSchoolInfo(@Param("school") School school);
    int selectCountSchoolInfo(@Param("school") School school);

    School selectSchoolInfo(@Param("school") School school);

    void insertSchoolInfo(@Param("school") School school);

    int updateSchoolInfo(@Param("school") School school);

    int deleteSchoolInfo(@Param("school") School school);
}
