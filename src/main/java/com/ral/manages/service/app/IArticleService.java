package com.ral.manages.service.app;

import com.ral.manages.entity.Result;
import com.ral.manages.entity.app.Article;

import java.util.Map;

public interface IArticleService {

    /*查询*/
    Result articlePagingQuery(Map<String,Object> map);
    Result articleEditQuery(Article article);
    /*新增*/
    Result articleInsert(Article article);
    /*修改*/
    Result articleUpdate(Article article);
    /*删除*/
    Result articleDelete(Article article);
    Result articleBatchDelete(Map<String,Object> map);
}
