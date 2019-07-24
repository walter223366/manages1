package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.entity.app.ZhaoShi;
import com.ral.manages.mapper.app.IZhaoShiMapper;
import com.ral.manages.service.app.IZhaoShiService;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ZhaoShiServiceImpl implements IZhaoShiService {

    private static final Logger LOG = LoggerFactory.getLogger(ZhaoShiServiceImpl.class);
    @Autowired
    private IZhaoShiMapper iZhaoShiMapper;

    /**分页查询*/
    @Override
    public GeneralResponse zhaoShiPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map),PageBean.pageSize(map));
        List<Map<String,Object>> zhaoShiList = iZhaoShiMapper.selectZhaoShiPagingQuery(map);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),zhaoShiList));
    }

    /**新增*/
    @Override
    public GeneralResponse zhaoShiAdd(ZhaoShi zhaoShi) {
        String msg = VerificationParams.verificationZhaoShi(zhaoShi);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iZhaoShiMapper.selectZhaoShiToName(zhaoShi);
        if(count > 0){
            return GeneralResponse.fail("新增失败，该招式名称已存在");
        }
        zhaoShi.setZhaoshi_id(StringUtil.getUUID());
        try{
            iZhaoShiMapper.insertZhaoShi(zhaoShi);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**修改*/
    @Override
    public GeneralResponse zhaoShiUpdate(ZhaoShi zhaoShi) {
        String msg = VerificationParams.verificationZhaoShi(zhaoShi);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iZhaoShiMapper.selectZhaoShiToExist(zhaoShi);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该招式ID不存在");
        }
        try{
            iZhaoShiMapper.updateZhaoShi(zhaoShi);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**删除*/
    @Override
    public GeneralResponse zhaoShiDelete(ZhaoShi zhaoShi) {
        int count = iZhaoShiMapper.selectZhaoShiToExist(zhaoShi);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该招式ID不存在");
        }
        try{
            iZhaoShiMapper.deleteZhaoShi(zhaoShi);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }
}
