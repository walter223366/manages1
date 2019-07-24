package com.ral.manages.service.app;

import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.ZhaoShi;
import java.util.Map;

public interface IZhaoShiService {

    /*查询*/
    GeneralResponse zhaoShiPagingQuery(Map<String,Object> map);
    /*新增*/
    GeneralResponse zhaoShiAdd(ZhaoShi zhaoShi);
    /*修改*/
    GeneralResponse zhaoShiUpdate(ZhaoShi zhaoShi);
    /*删除*/
    GeneralResponse zhaoShiDelete(ZhaoShi zhaoShi);
}
