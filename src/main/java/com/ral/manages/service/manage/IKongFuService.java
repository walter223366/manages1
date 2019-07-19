package com.ral.manages.service.manage;

import com.ral.manages.entity.manage.KongFu;
import com.ral.manages.exception.GeneralResponse;

import java.util.Map;

public interface IKongFuService {

    /*查询*/
    GeneralResponse kongFuPagingQuery(Map<String,Object> map);
    /*新增*/
    GeneralResponse kongFuAdd(KongFu kongFu);
    /*修改*/
    GeneralResponse kongFuUpdate(KongFu kongFu);
    /*删除*/
    GeneralResponse kongFuDelete(KongFu kongFu);

}
