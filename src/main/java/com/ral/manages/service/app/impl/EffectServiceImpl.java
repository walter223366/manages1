package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.emun.StateTable;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.entity.app.Effect;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.mapper.manage.IEffectMapper;
import com.ral.manages.service.app.IEffectService;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class EffectServiceImpl implements IEffectService {

    private static final Logger LOG = LoggerFactory.getLogger(EffectServiceImpl.class);
    @Autowired
    private IEffectMapper iEffectMapper;

    /**分页查询*/
    @Override
    public GeneralResponse effectPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> effectList = iEffectMapper.selectEffectPagingQuery(map);
        for(Map<String,Object> effectMap : effectList){
            int target = SetUtil.toMapValueInt(effectMap,"target");
            String target_value = (target==0?StateTable.Effect.TARGET_ZERO.getName():StateTable.Effect.TARGET_ONE.getName());
            effectMap.put("target",target_value);
        }
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),effectList));
    }

    /**新增*/
    @Override
    public GeneralResponse effectAdd(Effect effect) {
        effect.setEffect_id(StringUtil.getUUID());
        effect.setCancellation(StateTable.Effect.CANCELLATION_ZERO.getCode());
        try{
            iEffectMapper.insertEffect(effect);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**修改*/
    @Override
    public GeneralResponse effectUpdate(Effect effect) {
        String msg = VerificationParams.verificationEffect(effect);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iEffectMapper.selectEffectToExist(effect);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该效果ID不存在");
        }
        try{
            iEffectMapper.updateEffect(effect);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**删除*/
    @Override
    public GeneralResponse effectDelete(Effect effect) {
        int count = iEffectMapper.selectEffectToExist(effect);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该效果ID不存在");
        }
        effect.setCancellation(StateTable.Effect.CANCELLATION_ONE.getCode());
        try{
            iEffectMapper.deleteEffect(effect);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

}
