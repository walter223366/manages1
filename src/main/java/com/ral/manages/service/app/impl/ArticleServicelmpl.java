package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.entity.Result;
import com.ral.manages.util.VerificationUtil;
import com.ral.manages.entity.app.Article;
import com.ral.manages.mapper.app.IArticleMapper;
import com.ral.manages.service.app.IArticleService;
import com.ral.manages.util.Base64Util;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServicelmpl implements IArticleService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleServicelmpl.class);
    @Autowired
    private IArticleMapper iArticleMapper;


    /**
     * 分页查询
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result articlePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> articleList = iArticleMapper.articlePagingQuery(map);
        return Result.success(ResultCode.SUCCESS.getResult(),PageBean.resultPage(page.getTotal(),articleList));
    }

    /**
     * 编辑查询
     *
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public Result articleEditQuery(Article article) {
        if(StringUtil.isNull(article.getName())){
            return Result.fail("传入物品名称为空");
        }
        Map<String,Object> result = iArticleMapper.articleEditQuery(article);
        return Result.success(ResultCode.SUCCESS.getResult(),result);
    }

    /***
     * 新增
     *
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public Result articleInsert(Article article) {
        String msg = VerificationUtil.verificationArticle(article);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iArticleMapper.articleIsExist(article);
        if(count > 0){
            return Result.fail("新增失败，物品名称已存在");
        }
        article.setArticle_id(StringUtil.getUUID());
        article.setDeleteStatus(TableCode.Del.DELETE_ZERO.getCode());
        try{
            iArticleMapper.articleInsert(article);
            return Result.successNotdatas(ResultCode.SUCCESS.getResult());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getResult()+e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public Result articleUpdate(Article article) {
        if(StringUtil.isNull(article.getArticle_id())){
            return Result.fail("传入物品ID为空");
        }
        String msg = VerificationUtil.verificationArticle(article);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        Map<String,Object> map = iArticleMapper.articleIdQuery(article);
        if(SetUtil.isMapNull(map)){
            return Result.fail("修改失败，该物品不存在");
        }
        String name = SetUtil.toMapValueString(map,"name");
        if(!name.equals(article.getName())){
            int count = iArticleMapper.articleIsExist(article);
            if(count > 0){
                return Result.fail("修改失败，门派名称已存在");
            }
        }
        try{
            iArticleMapper.articleUpdate(article);
            return Result.successNotdatas(ResultCode.SUCCESS.getResult());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getResult()+e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param article article
     * @return GeneralResponse
     */
    @Override
    public Result articleDelete(Article article) {
        if(StringUtil.isNull(article.getName())){
            return Result.fail("传入物品名称为空");
        }
        int count = iArticleMapper.articleIsExist(article);
        if(count <= 0){
            return Result.fail("删除失败，该物品不存在");
        }
        article.setDeleteStatus(TableCode.Del.DELETE_ONE.getCode());
        try{
            iArticleMapper.articleDelete(article);
            return Result.successNotdatas(ResultCode.SUCCESS.getResult());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getResult()+e.getMessage());
        }
    }

    /**
     * 批量删除
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result articleBatchDelete(Map<String, Object> map) {
        List<Map<String,Object>> resluList = new ArrayList<Map<String,Object>>();
        String data = Base64Util.Base64Decode(SetUtil.toMapValueString(map,"data"));
        try{
            resluList = JSONArray.fromObject(data);
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail("传入data参数JSON格式错误");
        }
        if(SetUtil.isListNull(resluList)){
            return Result.fail("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : resluList){
                upMap.put("deleteStatus", TableCode.Del.DELETE_ONE.getCode());
                iArticleMapper.articleBatchDelete(upMap);
            }
            return Result.successNotdatas(ResultCode.SUCCESS.getResult());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getResult()+e.getMessage());
        }
    }
}
