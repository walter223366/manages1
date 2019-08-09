package com.ral.manages.controller.app;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.Account;
import com.ral.manages.entity.app.Article;
import com.ral.manages.service.app.IAccountService;
import com.ral.manages.service.app.IArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/article")
public class ArticleController {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private IArticleService iArticleService;


    /**
     * 分页查询
     * @param map map
     * @return Object
     */
    @RequestMapping("/pagingQuery")
    public Object articlePagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iArticleService.articlePagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 编辑查询
     * @param article article
     * @return Object
     */
    @RequestMapping("/editQuery")
    public Object articleEditQuery(Article article){
        LOG.info("请求参数:" + article);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iArticleService.articleEditQuery(article);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 新增
     * @param article article
     * @return Object
     */
    @RequestMapping("inAdd")
    public Object articleInsert(Article article){
        LOG.info("请求参数:" + article);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iArticleService.articleInsert(article);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 修改
     * @param article article
     * @return Object
     */
    @RequestMapping("inUpdate")
    public Object articleUpdate(Article article){
        LOG.info("请求参数:" + article);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iArticleService.articleUpdate(article);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 删除
     * @param article article
     * @return Object
     */
    @RequestMapping("inDelete")
    public Object accountDelete(Article article){
        LOG.info("请求参数:" + article);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iArticleService.articleDelete(article);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 批量删除
     * @param map map
     * @return Object
     */
    @RequestMapping("inBatchDelete")
    public Object accountBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iArticleService.articleBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
