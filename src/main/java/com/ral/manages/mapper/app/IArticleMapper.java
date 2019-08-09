package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.Article;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IArticleMapper {

    /*查询*/
    List<Map<String,Object>> articlePagingQuery(Map<String,Object> map);
    int articleIsExist(@Param("article") Article article);
    int articleIsName(@Param("article") Article article);
    Map<String,Object> articleEditQuery(@Param("article") Article article);
    /*新增*/
    void articleInsert(@Param("article") Article article);
    /*修改*/
    void articleUpdate(@Param("article") Article article);
    /*删除*/
    void articleDelete(@Param("article") Article article);
    void articleBatchDelete(Map<String,Object> map);

}
