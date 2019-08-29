package com.ral.manages.service.app;

import com.ral.manages.comms.response.GeneralResponse;
import com.ral.manages.entity.app.Article;

import java.util.Map;

public interface IArticleService {

    /*查询*/
    GeneralResponse articlePagingQuery(Map<String,Object> map);
    GeneralResponse articleEditQuery(Article article);
    /*新增*/
    GeneralResponse articleInsert(Article article);
    /*修改*/
    GeneralResponse articleUpdate(Article article);
    /*删除*/
    GeneralResponse articleDelete(Article article);
    GeneralResponse articleBatchDelete(Map<String,Object> map);
}
