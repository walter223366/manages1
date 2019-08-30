package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.entity.Result;
import com.ral.manages.comms.verifi.VerificationParams;
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
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result movePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map),PageBean.pageSize(map));
        List<Map<String,Object>> moveList = iMoveMapper.movePagingQuery(map);
        for(Map<String,Object> moveMap : moveList){
            String kongFuId = SetUtil.toMapValueString(moveMap,"kongfu_id");
            if(StringUtil.isNull(kongFuId)){
                moveMap.put("kongFuName","");
            }else{
                moveMap.put("kongFuName",seeKongFuName(kongFuId));
            }
        }
        return Result.success(ResultCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),moveList));
    }

    /**
     * 编辑查询
     *
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public Result moveEditQuery(Move move) {
        if(StringUtil.isNull(move.getName())){
            return Result.fail("传入招式名称为空");
        }
        Map<String,Object> result = iMoveMapper.moveEditQuery(move);
        return Result.success(ResultCode.SUCCESS.getMsg(),result);
    }

    /**
     * 新增
     *
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public Result moveInsert(Move move) {
        String msg = VerificationParams.verificationMove(move);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iMoveMapper.moveIsExist(move);
        if(count > 0){
            return Result.fail("新增失败，招式名称已存在");
        }
        move.setZhaoshi_id(StringUtil.getUUID());
        move.setDeleteStatus(TableCode.Del.DELETE_ZERO.getCode());
        try{
            iMoveMapper.moveInsert(move);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public Result moveUpdate(Move move) {
        if(StringUtil.isNull(move.getZhaoshi_id())){
            return Result.fail("传入招式ID为空");
        }
        String msg = VerificationParams.verificationMove(move);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        Map<String,Object> map = iMoveMapper.moveIdQuery(move);
        if(SetUtil.isMapNull(map)){
            return Result.fail("修改失败，该招式不存在");
        }
        String name = SetUtil.toMapValueString(map,"name");
        if(!name.equals(move.getName())){
            int count = iMoveMapper.moveIsExist(move);
            if(count > 0){
                return Result.fail("修改失败，招式名称已存在");
            }
        }
        try{
            iMoveMapper.moveUpdate(move);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public Result moveDelete(Move move) {
        if(StringUtil.isNull(move.getName())){
            return Result.fail("传入招式名称为空");
        }
        int count = iMoveMapper.moveIsExist(move);
        if(count <= 0){
            return Result.fail("删除失败，该招式不存在");
        }
        move.setDeleteStatus(TableCode.Del.DELETE_ONE.getCode());
        try{
            iMoveMapper.moveDelete(move);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 批量删除
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result moveBatchDelete(Map<String,Object> map) {
        List<Map<String,Object>> resluList = new ArrayList<Map<String,Object>>();
        String data = Base64Util.Base64Decode(SetUtil.toMapValueString(map,"data"));
        try{
            resluList = JSONArray.fromObject(data);
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail("传入data参数JSON格式错误");
        }
        if(SetUtil.isListNull(resluList)){
            return Result.fail("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : resluList){
                upMap.put("deleteStatus", TableCode.Del.DELETE_ONE.getCode());
                iMoveMapper.moveBatchDelete(upMap);
            }
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 效果下拉框
     *
     * @return GeneralResponse
     */
    @Override
    public Result moveAddEffect() {
        List<Map<String,Object>> resultList = iEffectMapper.effectQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return Result.success(ResultCode.SUCCESS.getMsg(),resultMap);
    }

    /**
     * 功夫下拉框
     *
     * @return GeneralResponse
     */
    @Override
    public Result moveAddKongFu() {
        List<Map<String,Object>> resultList = iKongFuMapper.kongFuQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return Result.success(ResultCode.SUCCESS.getMsg(),resultMap);
    }

    /**
     * 查看详情
     *
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public Result moveSee(Move move) {
        if(StringUtil.isNull(move.getName())){
            return Result.fail("传入招式名称为空");
        }
        Map<String,Object> resultMap = iMoveMapper.moveEditQuery(move);
        String kongFuId = SetUtil.toMapValueString(resultMap,"kongfu_id");
        if(StringUtil.isNull(kongFuId)){
            resultMap.put("kongFuName","");
        }else{
            resultMap.put("kongFuName",seeKongFuName(kongFuId));
        }
        String effectId = SetUtil.toMapValueString(resultMap,"zhaoshi_effect");
        if(StringUtil.isNull(effectId)){
            resultMap.put("effectName","");
        }else{
            resultMap.put("effectName",seeEffectName(effectId));
        }
        return Result.success(ResultCode.SUCCESS.getMsg(),resultMap);
    }

    /**
     * 获取功夫名称
     *
     * @param str str
     * @return  List<String>
     */
    private List<String> seeKongFuName(String str){
        String[] values = str.split(",");
        List<String> list = new ArrayList<String>();
        for(String value : values){
            list.add(value);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("kIds",list);
        List<Map<String,Object>> resultList = iKongFuMapper.kongFuQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(SetUtil.toMapValueString(map,"name"));
        }
        return nameList;
    }

    /**
     * 获取效果名称
     *
     * @param str str
     * @return  List<String>
     */
    private List<String> seeEffectName(String str){
        String[] values = str.split(",");
        List<String> list = new ArrayList<String>();
        for(String value : values){
            list.add(value);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("eIds",list);
        List<Map<String,Object>> resultList = iEffectMapper.effectQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(SetUtil.toMapValueString(map,"name"));
        }
        return nameList;
    }
}
