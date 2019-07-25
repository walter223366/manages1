package com.ral.manages.service.app;

import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.Article;

import java.util.Map;

public interface IArticleService {

    /*查询*/
    GeneralResponse articlePagingQuery(Map<String,Object> map);
    /*新增*/
    GeneralResponse articleAdd(Article article);
    /*修改*/
    GeneralResponse articleUpdate(Article article);
    /*删除*/
    GeneralResponse articleDelete(Article article);
}
