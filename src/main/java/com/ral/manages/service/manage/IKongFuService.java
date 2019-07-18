package com.ral.manages.service.manage;

import com.ral.manages.exception.GeneralResponse;

import java.util.Map;

public interface IKongFuService {

    /*查询*/
    GeneralResponse kongFuPagingQuery(Map<String,Object> map);
}
