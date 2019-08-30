package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.verifi.VerificationParams;
import com.ral.manages.entity.app.Effect;
import com.ral.manages.entity.Result;
import com.ral.manages.mapper.app.IEffectMapper;
import com.ral.manages.service.app.IEffectService;
import com.ral.manages.comms.page.PageBean;
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
public class EffectServiceImpl implements IEffectService {

    private static final Logger LOG = LoggerFactory.getLogger(EffectServiceImpl.class);
    @Autowired
    private IEffectMapper iEffectMapper;

    /**
     * 分页查询
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result effectPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> effectList = iEffectMapper.effectPagingQuery(map);
        for(Map<String,Object> effectMap : effectList){
            int target = SetUtil.toMapValueInt(effectMap,"target");
            String target_value = (target==0? TableCode.Effect.TARGET_ZERO.getName(): TableCode.Effect.TARGET_ONE.getName());
            effectMap.put("target",target_value);
        }
        return Result.success(ResultCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),effectList));
    }

    /**
     * 编辑查询
     *
     * @param effect effect
     * @return GeneralResponse
     */
    @Override
    public Result effectEditQuery(Effect effect) {
        if(StringUtil.isNull(effect.getName())){
            return Result.fail("传入效果名称错误");
        }
        Map<String,Object> result = iEffectMapper.effectEditQuery(effect);
        return Result.success(ResultCode.SUCCESS.getMsg(),result);
    }

    /**
     * 新增
     *
     * @param effect effect
     * @return GeneralResponse
     */
    @Override
    public Result effectInsert(Effect effect) {
        String msg = VerificationParams.verificationEffect(effect);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iEffectMapper.effectIsExist(effect);
        if(count > 0){
            return Result.fail("新增失败，效果名称已存在");
        }
        effect.setEffect_id(StringUtil.getUUID());
        effect.setDeleteStatus(TableCode.Del.DELETE_ZERO.getCode());
        try{
            iEffectMapper.effectInsert(effect);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param effect effect
     * @return GeneralResponse
     */
    @Override
    public Result effectUpdate(Effect effect) {
        if(StringUtil.isNull(effect.getEffect_id())){
            return Result.fail("传入效果ID为空");
        }
        String msg = VerificationParams.verificationEffect(effect);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        Map<String,Object> map = iEffectMapper.effectIdQuery(effect);
        if(SetUtil.isMapNull(map)){
            return Result.fail("修改失败，该效果不存在");
        }
        String name = SetUtil.toMapValueString(map,"name");
        if(!name.equals(effect.getName())){
            int count = iEffectMapper.effectIsExist(effect);
            if(count > 0){
                return Result.fail("修改失败，效果名称已存在");
            }
        }
        try{
            iEffectMapper.effectUpdate(effect);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param effect effect
     * @return GeneralResponse
     */
    @Override
    public Result effectDelete(Effect effect) {
        if(StringUtil.isNull(effect.getName())){
            return Result.fail("传入效果名称为空");
        }
        int count = iEffectMapper.effectIsExist(effect);
        if(count <= 0){
            return Result.fail("删除失败，该效果不存在");
        }
        effect.setDeleteStatus(TableCode.Del.DELETE_ONE.getCode());
        try{
            iEffectMapper.effectDelete(effect);
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
    public Result effectBatchDelete(Map<String,Object> map) {
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
                iEffectMapper.effectBatchDelete(upMap);
            }
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }
}
