package com.ral.manages.mapper.manage;

import com.ral.manages.entity.manage.School;
import java.util.List;

public interface ISchoolMapper {

    List<School> selectPageSchoolInfo(School school);
    int selectCountSchoolInfo(School school);

    School selectSchoolInfo(School school);
    
    void insertSchoolInfo(School school);

    int updateSchoolInfo(School school);

    int deleteSchoolInfo(School school);
}
