package com.ral.manages.service.app;

import com.ral.manages.entity.app.Effect;
import com.ral.manages.comms.response.GeneralResponse;
import java.util.Map;

public interface IEffectService {

    /*查询*/
    GeneralResponse effectPagingQuery(Map<String,Object> map);
    GeneralResponse effectEditQuery(Effect effect);
    /*新增*/
    GeneralResponse effectInsert(Effect effect);
    /*修改*/
    GeneralResponse effectUpdate(Effect effect);
    /*删除*/
    GeneralResponse effectDelete(Effect effect);
    GeneralResponse effectBatchDelete(Map<String,Object> map);
}
