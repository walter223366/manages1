package com.ral.manages.service.app;

import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.Move;
import java.util.Map;

public interface IMoveService {

    /*查询*/
    GeneralResponse movePagingQuery(Map<String,Object> map);
    GeneralResponse moveEditQuery(Move move);
    /*新增*/
    GeneralResponse moveInsert(Move move);
    /*修改*/
    GeneralResponse moveUpdate(Move move);
    /*删除*/
    GeneralResponse moveDelete(Move move);
    GeneralResponse moveBatchDelete(Map<String,Object> map);
    /*添加效果下拉框*/
    GeneralResponse moveAddEffect();
    /*添加功夫下拉框*/
    GeneralResponse moveAddKongFu();

}
