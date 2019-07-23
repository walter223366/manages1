package com.ral.manages.service.app;

import com.ral.manages.entity.app.Effect;
import com.ral.manages.commom.response.GeneralResponse;
import java.util.Map;

public interface IEffectService {

    /*查询*/
    GeneralResponse effectPagingQuery(Map<String,Object> map);
    /*新增*/
    GeneralResponse effectAdd(Effect effect);
    /*修改*/
    GeneralResponse effectUpdate(Effect effect);
    /*删除*/
    GeneralResponse effectDelete(Effect effect);
}
