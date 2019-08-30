package com.ral.manages.service.app;

import com.ral.manages.entity.app.KongFu;
import com.ral.manages.entity.Result;
import java.util.Map;

public interface IKongFuService {

    /*查询*/
    Result kongFuPagingQuery(Map<String,Object> map);
    Result kongFuEditQuery(KongFu kongFu);
    Result kongFuAddMove();
    /*新增*/
    Result kongFuInsert(KongFu kongFu);
    /*修改*/
    Result kongFuUpdate(KongFu kongFu);
    /*删除*/
    Result kongFuDelete(KongFu kongFu);
    Result kongFuBatchDelete(Map<String,Object> map);
    /*查看*/
    Result kongFuSee(KongFu kongFu);
}
