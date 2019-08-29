package com.ral.manages.service.app;

import com.ral.manages.entity.app.KongFu;
import com.ral.manages.comms.response.GeneralResponse;
import java.util.Map;

public interface IKongFuService {

    /*查询*/
    GeneralResponse kongFuPagingQuery(Map<String,Object> map);
    GeneralResponse kongFuEditQuery(KongFu kongFu);
    GeneralResponse kongFuAddMove();
    /*新增*/
    GeneralResponse kongFuInsert(KongFu kongFu);
    /*修改*/
    GeneralResponse kongFuUpdate(KongFu kongFu);
    /*删除*/
    GeneralResponse kongFuDelete(KongFu kongFu);
    GeneralResponse kongFuBatchDelete(Map<String,Object> map);
    /*查看*/
    GeneralResponse kongFuSee(KongFu kongFu);
}
