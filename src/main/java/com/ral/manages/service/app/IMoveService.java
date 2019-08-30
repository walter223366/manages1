package com.ral.manages.service.app;

import com.ral.manages.entity.Result;
import com.ral.manages.entity.app.Move;
import java.util.Map;

public interface IMoveService {

    /*查询*/
    Result movePagingQuery(Map<String,Object> map);
    Result moveEditQuery(Move move);
    /*新增*/
    Result moveInsert(Move move);
    /*修改*/
    Result moveUpdate(Move move);
    /*删除*/
    Result moveDelete(Move move);
    Result moveBatchDelete(Map<String,Object> map);
    /*效果下拉框*/
    Result moveAddEffect();
    /*功夫下拉框*/
    Result moveAddKongFu();
    /*查看*/
    Result moveSee(Move move);
}
