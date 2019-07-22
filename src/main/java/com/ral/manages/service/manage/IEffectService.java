package com.ral.manages.service.manage;

import com.ral.manages.exception.GeneralResponse;
import java.util.Map;

public interface IEffectService {

    /*查询*/
    GeneralResponse effectPagingQuery(Map<String,Object> map);
}
