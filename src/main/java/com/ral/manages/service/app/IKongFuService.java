package com.ral.manages.service.app;

import com.ral.manages.entity.app.KongFu;
import com.ral.manages.commom.response.GeneralResponse;

import java.util.Map;

public interface IKongFuService {

    /*查询*/
    GeneralResponse kongFuPagingQuery(Map<String,Object> map);
    GeneralResponse kongFuEditQuery(KongFu kongFu);
    GeneralResponse kongFuQueryMove();
    /*新增*/
    GeneralResponse kongFuInsert(KongFu kongFu);
    /*修改*/
    GeneralResponse kongFuUpdate(KongFu kongFu);
    /*删除*/
    GeneralResponse kongFuDelete(KongFu kongFu);
    /*详情*/
    GeneralResponse kongFuDetails(KongFu kongFu);
}
