package com.ral.manages.service.manage;

import com.ral.manages.entity.manage.School;
import com.ral.manages.util.page.PageBean;

public interface ISchoolService {

    PageBean<?> pageCheckSchoolInfo(School school);


}
