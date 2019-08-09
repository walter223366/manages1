package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.emun.StateTable;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.entity.app.Article;
import com.ral.manages.mapper.app.IArticleMapper;
import com.ral.manages.service.app.IArticleService;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServicelmpl implements IArticleService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleServicelmpl.class);
    @Autowired
    private IArticleMapper iArticleMapper;


    /**
     * 分页查询
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse articlePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> articleList = iArticleMapper.articlePagingQuery(map);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),articleList));
    }

    /**
     * 编辑查询
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse articleEditQuery(Article article) {
        Map<String,Object> result = iArticleMapper.articleEditQuery(article);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),result);
    }

    /***
     * 新增
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse articleInsert(Article article) {
        String msg = VerificationParams.verificationArticle(article);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iArticleMapper.articleIsName(article);
        if(count > 0){
            return GeneralResponse.fail("新增失败，物品名称已存在");
        }
        article.setArticle_id(StringUtil.getUUID());
        try{
            iArticleMapper.articleInsert(article);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse articleUpdate(Article article) {
        String msg = VerificationParams.verificationArticle(article);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iArticleMapper.articleIsExist(article);
        if(count > 0){
            return GeneralResponse.fail("修改失败，该物品已存在");
        }
        try{
            iArticleMapper.articleUpdate(article);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse articleDelete(Article article) {
        int count = iArticleMapper.articleIsExist(article);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该物品不存在");
        }
        article.setCancellation(StateTable.User.CANCELLATION_ONE.getCode());
        try{
            iArticleMapper.articleDelete(article);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }
}
