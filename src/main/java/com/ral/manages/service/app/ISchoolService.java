package com.ral.manages.service.app;

import com.ral.manages.entity.app.School;
import com.ral.manages.entity.Result;
import java.util.Map;

public interface ISchoolService {

    /*查询*/
    Result schoolPagingQuery(Map<String,Object> map);
    Result schoolEditQuery(School school);
    /*新增*/
    Result schoolInsert(School school);
    /*修改*/
    Result schoolUpdate(School school);
    /*删除*/
    Result schoolDelete(School school);
    Result schoolBatchDelete(Map<String,Object> map);
}
