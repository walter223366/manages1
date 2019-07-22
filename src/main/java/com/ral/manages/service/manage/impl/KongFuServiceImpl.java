package com.ral.manages.service.manage.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.emun.StateTable;
import com.ral.manages.entity.manage.KongFu;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.exception.VerificationParams;
import com.ral.manages.mapper.manage.IKongFuMapper;
import com.ral.manages.service.manage.IKongFuService;
import com.ral.manages.util.PageUtil;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class KongFuServiceImpl implements IKongFuService {

    private static final Logger LOG = LoggerFactory.getLogger(KongFuServiceImpl.class);

    @Autowired
    private IKongFuMapper iKongFuMapper;

    /**分页查询*/
    @Override
    public GeneralResponse kongFuPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageUtil.pageNum(map),PageUtil.pageSize(map));
        List<Map<String,Object>> kongFuList = iKongFuMapper.selectKongFuPagingQuery(map);
        for(Map<String,Object> kongFuMap : kongFuList){
            kongFuMap.put("type",kongFuType(SetUtil.toMapValueInt(kongFuMap,"type")));
        }
        return GeneralResponse.success("操作成功",PageUtil.resultPage(page.getTotal(),SetUtil.clearValueNullToList(kongFuList)));
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
        try{
            iKongFuMapper.insertKongFu(kongFu);
            return GeneralResponse.successNotdatas("新增成功");
        }catch (Exception e){
            LOG.debug("新增失败，"+e.getMessage(),e);
            return GeneralResponse.fail("新增失败，"+e.getMessage());
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
            return GeneralResponse.successNotdatas("修改成功");
        }catch (Exception e){
            LOG.debug("修改失败，"+e.getMessage(),e);
            return GeneralResponse.fail("修改失败，"+e.getMessage());
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
        kongFu.setCancellation(1);
        try{
            iKongFuMapper.deleteKongFu(kongFu);
            return GeneralResponse.successNotdatas("删除成功");
        }catch (Exception e){
            LOG.debug("删除失败，"+e.getMessage(),e);
            return GeneralResponse.fail("删除失败，"+e.getMessage());
        }
    }

    private String kongFuType(int type){
        switch (type){
            case 0:return StateTable.KONGFU_TYPE_ZERO.getName();
            case 1:return StateTable.KONGFU_TYPE_ONE.getName();
            case 2:return StateTable.KONGFU_TYPE_TWO.getName();
            case 3:return StateTable.KONGFU_TYPE_THREE.getName();
            case 4:return StateTable.KONGFU_TYPE_FOUR.getName();
            case 5:return StateTable.KONGFU_TYPE_FIVES.getName();
            case 6:return StateTable.KONGFU_TYPE_SIX.getName();
            case 7:return StateTable.KONGFU_TYPE_SEVEN.getName();
            default:return "其他";
        }
    }
}
