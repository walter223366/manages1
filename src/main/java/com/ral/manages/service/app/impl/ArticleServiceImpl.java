package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.*;
import com.ral.manages.mapper.app.IArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("article")
public class ArticleServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    private IArticleMapper iArticleMapper;

    /**
     * 处理物品管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.PAGINGQUERY: result = articlePagingQuery(map);
                break;
            case ProjectConst.EDITQUERY: result = articleEditQuery(map);
                break;
            case ProjectConst.INSERT: result = articleInsert(map);
                break;
            case ProjectConst.UPDATE: result = articleUpdate(map);
                break;
            case ProjectConst.DELETE: result = articleDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> articlePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> articleList = iArticleMapper.articlePagingQuery(map);
        return PageBean.resultPage(page.getTotal(),articleList);
    }

    /*编辑查询*/
    private Map<String,Object> articleEditQuery(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入物品名称为空");
        }
        return iArticleMapper.articleEditQuery(map);
    }

    /*新增*/
    private Map<String,Object> articleInsert(Map<String,Object> map) {
        VerificationUtil.verificationArticle(map);
        int count = iArticleMapper.articleIsExist(map);
        if(count > 0){
           throw new BizException("新增失败，物品名称已存在");
        }
        map.put("id",StringUtil.getUUID());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            iArticleMapper.articleInsert(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> articleUpdate(Map<String,Object> map) {
        String article_id = MapUtil.getString(map,"article_id");
        if(StringUtil.isNull(article_id)){
            throw new BizException("传入物品ID为空");
        }
        VerificationUtil.verificationArticle(map);
        Map<String,Object> qMap = iArticleMapper.articleIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
           throw new BizException("修改失败，该物品不存在");
        }
        String name = MapUtil.getString(map,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = iArticleMapper.articleIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，门派名称已存在");
            }
        }
        try{
            iArticleMapper.articleUpdate(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> articleDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String, Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
           throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                iArticleMapper.articleDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("批量删除失败："+e.getMessage());
            throw new BizException("批量删除失败："+e.getMessage());
        }
    }
}
