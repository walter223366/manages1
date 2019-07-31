package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.emun.StateTable;
import com.ral.manages.entity.app.KongFu;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import com.ral.manages.service.app.IKongFuService;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KongFuServiceImpl implements IKongFuService {

    private static final Logger LOG = LoggerFactory.getLogger(KongFuServiceImpl.class);
    @Autowired
    private IKongFuMapper iKongFuMapper;
    @Autowired
    private IMoveMapper iMoveMapper;

    /**分页查询*/
    @Override
    public GeneralResponse kongFuPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> kongFuList = iKongFuMapper.selectKongFuPagingQuery(map);
        for(Map<String,Object> kongFuMap : kongFuList){
            kongFuMap.put("type",kongFuType(SetUtil.toMapValueInt(kongFuMap,"type")));
        }
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),kongFuList));
    }

    /**招式管理选项查询*/
    @Override
    public GeneralResponse kongFuQueryMove() {
        List<Map<String,Object>> kongFuList = iKongFuMapper.selectKongFuToNameId();
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("datas",kongFuList);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),result);
    }

    /**新增*/
    @Override
    public GeneralResponse kongFuAdd(KongFu kongFu) {
        String msg = VerificationParams.verificationKongFu(kongFu);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iKongFuMapper.selectKongFuToExist(kongFu);
        if(count > 0){
            return GeneralResponse.fail("新增失败，该功夫名称已存在");
        }
        kongFu.setKongfu_id(StringUtil.getUUID());
        kongFu.setCancellation(StateTable.KongFu.CANCELLATION_ZERO.getCode());
        try{
            iKongFuMapper.insertKongFu(kongFu);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**修改*/
    @Override
    public GeneralResponse kongFuUpdate(KongFu kongFu) {
        String msg = VerificationParams.verificationKongFu(kongFu);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iKongFuMapper.selectKongFuToExist(kongFu);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该功夫名称不存在");
        }
        kongFu.setKongfu_id(StringUtil.getUUID());
        try{
            iKongFuMapper.updateKongFu(kongFu);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**删除*/
    @Override
    public GeneralResponse kongFuDelete(KongFu kongFu) {
        String msg = VerificationParams.verificationKongFu(kongFu);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iKongFuMapper.selectKongFuToExist(kongFu);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该功夫名称不存在");
        }
        kongFu.setCancellation(StateTable.KongFu.CANCELLATION_ONE.getCode());
        try{
            iKongFuMapper.deleteKongFu(kongFu);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**详情*/
    @Override
    public GeneralResponse kongFuDetails(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getName())){
            return GeneralResponse.fail("功夫名称为空");
        }
        Map<String,Object> map = iKongFuMapper.selectKongFuDetails(kongFu);
        if(SetUtil.isMapNull(map)){
            return GeneralResponse.fail("该功夫名称不存在");
        }
        Map<String,Object> valueMap = SetUtil.clearValueNullToMap(map);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String union = SetUtil.toMapValueString(valueMap,"kongfu_zhaoshi");
        valueMap.put("move",list);
        if(StringUtil.isNull(union)){
            return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),valueMap);
        }else{
            String[] array = StringUtil.arrayToString(union);
            for(String id : array){
                Map<String,Object> moveMap = iMoveMapper.selectMoveName(id);
                list.add(moveMap);
            }
            return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),valueMap);
        }
    }

    /**处理功夫类型*/
    private String kongFuType(int type){
        switch (type){
            case 0:return StateTable.KongFu.TYPE_ZERO.getName();
            case 1:return StateTable.KongFu.TYPE_ONE.getName();
            case 2:return StateTable.KongFu.TYPE_TWO.getName();
            case 3:return StateTable.KongFu.TYPE_THREE.getName();
            case 4:return StateTable.KongFu.TYPE_FOUR.getName();
            case 5:return StateTable.KongFu.TYPE_FIVES.getName();
            case 6:return StateTable.KongFu.TYPE_SIX.getName();
            case 7:return StateTable.KongFu.TYPE_SEVEN.getName();
            default:return "其他";
        }
    }
}
