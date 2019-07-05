package com.ral.manages.mapper.manage;

import com.ral.manages.entity.manage.Article;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface IArticleMapper {

    //查询
    List<Article> selectArticle(@Param("article") Article article);
    //新增
    void insertAritcle(@Param("article") Article article);
    //修改
    void updateAritcle(@Param("article") Article article);
    //删除
    void deleteAritcle(@Param("article") Article article);
}
