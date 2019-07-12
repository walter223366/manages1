package com.ral.manages.service.manage;

import com.ral.manages.entity.manage.School;

public interface ISchoolService {

    String pageCheckSchoolInfo(School school);

    void insertSchoolInfo(School school);

    void updateSchoolInfo(School school);

    void deleteSchoolInfo(School school);
}
