package com.ral.manages.service.app;

import com.ral.manages.entity.app.KongFu;
import com.ral.manages.commom.response.GeneralResponse;

import java.util.Map;

public interface IKongFuService {

    /*查询*/
    GeneralResponse kongFuPagingQuery(Map<String,Object> map);
    GeneralResponse kongFuOrZhaoShi();
    /*新增*/
    GeneralResponse kongFuAdd(KongFu kongFu);
    /*修改*/
    GeneralResponse kongFuUpdate(KongFu kongFu);
    /*删除*/
    GeneralResponse kongFuDelete(KongFu kongFu);

}