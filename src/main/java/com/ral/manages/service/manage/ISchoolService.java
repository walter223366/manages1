package com.ral.manages.service.manage;

import com.ral.manages.exception.GeneralResponse;
import java.util.Map;

public interface ISchoolService {

    //查询
    GeneralResponse schoolPagingQuery(Map<String,Object> map);


}
