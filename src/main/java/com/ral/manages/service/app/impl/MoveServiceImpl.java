package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.emun.StateTable;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.entity.app.Move;
import com.ral.manages.mapper.app.IEffectMapper;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import com.ral.manages.service.app.IMoveService;
import com.ral.manages.util.Base64Util;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MoveServiceImpl implements IMoveService {

    private static final Logger LOG = LoggerFactory.getLogger(MoveServiceImpl.class);
    @Autowired
    private IMoveMapper iMoveMapper;
    @Autowired
    private IKongFuMapper iKongFuMapper;
    @Autowired
    private IEffectMapper iEffectMapper;

    /**
     * 分页查询
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse movePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map),PageBean.pageSize(map));
        List<Map<String,Object>> zhaoShiList = iMoveMapper.movePagingQuery(map);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),zhaoShiList));
    }

    /**
     * 编辑查询
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveEditQuery(Move move) {
        Map<String,Object> result = iMoveMapper.moveEditQuery(move);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),result);
    }

    /**
     * 新增
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveInsert(Move move) {
        String msg = VerificationParams.verificationZhaoShi(move);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iMoveMapper.moveIsName(move);
        if(count > 0){
            return GeneralResponse.fail("新增失败，招式名称已存在");
        }
        move.setZhaoshi_id(StringUtil.getUUID());
        move.setDeleteStatus(StateTable.Del.DELETE_ZERO.getCode());
        try{
            iMoveMapper.moveInsert(move);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveUpdate(Move move) {
        String msg = VerificationParams.verificationZhaoShi(move);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iMoveMapper.moveIsExist(move);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该招式不存在");
        }
        int num = iMoveMapper.moveIsName(move);
        if(num > 0){
            return GeneralResponse.fail("修改失败，招式名称已存在");
        }
        try{
            iMoveMapper.moveUpdate(move);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveDelete(Move move) {
        int count = iMoveMapper.moveIsExist(move);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该招式不存在");
        }
        move.setDeleteStatus(StateTable.Del.DELETE_ONE.getCode());
        try{
            iMoveMapper.moveDelete(move);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveBatchDelete(Map<String,Object> map) {
        List<Map<String,Object>> resluList = new ArrayList<Map<String,Object>>();
        String data = Base64Util.Base64Decode(SetUtil.toMapValueString(map,"data"));
        try{
            resluList = JSONArray.fromObject(data);
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail("传入data参数JSON格式错误");
        }
        if(SetUtil.isListNull(resluList)){
            return GeneralResponse.fail("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : resluList){
                upMap.put("deleteStatus", StateTable.Del.DELETE_ONE.getCode());
                iMoveMapper.moveBatchDelete(upMap);
            }
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 添加效果下拉框
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveAddEffect() {
        List<Map<String,Object>> resultList = iEffectMapper.effectQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),resultMap);
    }

    /**
     * 添加功夫下拉框
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveAddKongFu() {
        List<Map<String,Object>> resultList = iKongFuMapper.kongFuQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),resultMap);
    }
}
