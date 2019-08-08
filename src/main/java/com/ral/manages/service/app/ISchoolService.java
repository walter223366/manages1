package com.ral.manages.service.app;

import com.ral.manages.entity.app.School;
import com.ral.manages.commom.response.GeneralResponse;
import java.util.Map;

public interface ISchoolService {

    /*查询*/
    GeneralResponse schoolPagingQuery(Map<String,Object> map);
    GeneralResponse schoolEditQuery(School school);
    /*新增*/
    GeneralResponse schoolInsert(School school);
    /*修改*/
    GeneralResponse schoolUpdate(School school);
    /*删除*/
    GeneralResponse schoolDelete(School school);
}
