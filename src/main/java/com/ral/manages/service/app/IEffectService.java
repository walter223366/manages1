package com.ral.manages.service.app;

import com.ral.manages.entity.app.Effect;
import com.ral.manages.entity.Result;
import java.util.Map;

public interface IEffectService {

    /*查询*/
    Result effectPagingQuery(Map<String,Object> map);
    Result effectEditQuery(Effect effect);
    /*新增*/
    Result effectInsert(Effect effect);
    /*修改*/
    Result effectUpdate(Effect effect);
    /*删除*/
    Result effectDelete(Effect effect);
    Result effectBatchDelete(Map<String,Object> map);
}
