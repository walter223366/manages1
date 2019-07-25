package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.Article;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IArticleMapper {

    /*查询*/
    List<Map<String,Object>> selectArticlePagingQuery(Map<String,Object> map);
    int selectArticleToExist(@Param("article") Article article);
    int selectArticleToName(@Param("article") Article article);
    /*新增*/
    void insertArticle(@Param("article") Article article);
    /*修改*/
    void updateArticle(@Param("article") Article article);
    /*删除*/
    void deleteArticle(@Param("article") Article article);

}
