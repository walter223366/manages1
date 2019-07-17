package com.ral.manages.service.manage;

import com.ral.manages.entity.manage.School;
import com.ral.manages.exception.GeneralResponse;
import java.util.Map;

public interface ISchoolService {

    /*查询*/
    GeneralResponse schoolPagingQuery(Map<String,Object> map);
    /*新增*/
    GeneralResponse schoolAdd(School school);
    /*修改*/
    GeneralResponse schoolUpdate(School school);
    /*删除*/
    GeneralResponse schoolDelete(School school);
}
