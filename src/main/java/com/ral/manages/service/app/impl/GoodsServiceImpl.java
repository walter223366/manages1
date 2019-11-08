package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.*;
import com.ral.manages.mapper.app.IGoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("goods")
public class GoodsServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);
    @Autowired
    private IGoodsMapper goodsMapper;

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
            case ProjectConst.PAGINGQUERY: result = goodsPagingQuery(map);
                break;
            case ProjectConst.SEEQUERY: result = goodsSee(map);
                break;
            case ProjectConst.INSERT: result = goodsInsert(map);
                break;
            case ProjectConst.UPDATE: result = goodsUpdate(map);
                break;
            case ProjectConst.DELETE: result = goodsDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> goodsPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> goodsList = goodsMapper.goodsPagingQuery(map);
        for(Map<String,Object> artMap : goodsList){
            artMap.put("typeValue",articleType(MapUtil.getString(artMap,"type")));
            artMap.put("weaponValue",weaponType(MapUtil.getString(artMap,"weapon_type")));
        }
        return PageBean.resultPage(page.getTotal(),goodsList);
    }

    /*新增*/
    private Map<String,Object> goodsInsert(Map<String,Object> map) {
        VerificationUtil.verificationGoods(map);
        int count = goodsMapper.goodsIsExist(map);
        if(count > 0){
           throw new BizException("新增失败，物品名称已存在");
        }
        map.put("goods_id",StringUtil.getUUID());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            goodsMapper.goodsInsert(SetUtil.turnNull(map));
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> goodsUpdate(Map<String,Object> map) {
        String article_id = MapUtil.getString(map,"goods_id");
        if(StringUtil.isNull(article_id)){
            throw new BizException("传入物品ID为空");
        }
        VerificationUtil.verificationGoods(map);
        Map<String,Object> qMap = goodsMapper.goodsIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
           throw new BizException("修改失败，该物品不存在");
        }
        String name = MapUtil.getString(map,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = goodsMapper.goodsIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，门派名称已存在");
            }
        }
        try{
            goodsMapper.goodsUpdate(SetUtil.turnNull(map));
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> goodsDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String, Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
           throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                goodsMapper.goodsDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*详情*/
    private Map<String,Object> goodsSee(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入物品名称为空");
        }
        return goodsMapper.goodsEditQuery(map);
    }

    private String articleType(String type){
        switch (type){
            case "0":return TableCode.ARTICLE_ZERO.getName();
            case "1":return TableCode.ARTICLE_ONE.getName();
            case "2":return TableCode.ARTICLE_TWO.getName();
            case "3":return TableCode.ARTICLE_THREE.getName();
            case "4":return TableCode.ARTICLE_FOUR.getName();
            case "5":return TableCode.ARTICLE_FIVES.getName();
            case "6":return TableCode.ARTICLE_SIX.getName();
            default:return "其他";
        }
    }

    private String weaponType(String type){
        switch (type){
            case "0":return TableCode.TYPE_TWO.getName();
            case "1":return TableCode.TYPE_THREE.getName();
            case "2":return TableCode.TYPE_FOUR.getName();
            case "3":return TableCode.TYPE_FIVES.getName();
            case "4":return TableCode.TYPE_SIX.getName();
            case "5":return TableCode.TYPE_SEVEN.getName();
            default:return "其他";
        }
    }
}
