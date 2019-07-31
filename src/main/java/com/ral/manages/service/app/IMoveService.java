package com.ral.manages.service.app;

import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.Move;
import java.util.Map;

public interface IMoveService {

    /*查询*/
    GeneralResponse movePagingQuery(Map<String,Object> map);
    /*新增*/
    GeneralResponse moveAdd(Move move);
    /*修改*/
    GeneralResponse moveUpdate(Move move);
    /*删除*/
    GeneralResponse moveDelete(Move move);
    /*详情*/
    GeneralResponse moveDetails(Move move);
}
