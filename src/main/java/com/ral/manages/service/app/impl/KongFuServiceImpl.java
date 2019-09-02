package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.entity.app.KongFu;
import com.ral.manages.entity.Result;
import com.ral.manages.util.VerificationUtil;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import com.ral.manages.service.app.IKongFuService;
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

    /**
     * 分页查询
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result kongFuPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> kongFuList = iKongFuMapper.kongFuPagingQuery(map);
        for(Map<String,Object> kongFuMap : kongFuList){
            kongFuMap.put("type",kongFuType(SetUtil.toMapValueInt(kongFuMap,"type")));
            int enable = SetUtil.toMapValueInt(kongFuMap,"enable");
            String enableValue = (enable== TableCode.KongFu.ENABLE_ONE.getCode()? TableCode.KongFu.ENABLE_ONE.getName(): TableCode.KongFu.ENABLE_ZERO.getName());
            kongFuMap.put("enable",enableValue);
        }
        return Result.success(ResultCode.SUCCESS.getResult(),PageBean.resultPage(page.getTotal(),kongFuList));
    }

    /**
     * 编辑查询
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public Result kongFuEditQuery(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getName())){
            return Result.fail("传入功夫名称为空");
        }
        Map<String,Object> result = iKongFuMapper.kongFuEditQuery(kongFu);
        return Result.success(ResultCode.SUCCESS.getResult(),result);
    }

    /**
     * 招式下拉框
     *
     * @return GeneralResponse
     */
    @Override
    public Result kongFuAddMove() {
        List<Map<String,Object>> kongFuList = iMoveMapper.moveQueryMarquee();
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("data",kongFuList);
        return Result.success(ResultCode.SUCCESS.getResult(),result);
    }

    /**
     * 新增
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public Result kongFuInsert(KongFu kongFu) {
        String msg = VerificationUtil.verificationKongFu(kongFu);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iKongFuMapper.kongFuIsExist(kongFu);
        if(count > 0){
            return Result.fail("新增失败，功夫名称已存在");
        }
        kongFu.setKongfu_id(StringUtil.getUUID());
        kongFu.setDeleteStatus(TableCode.Del.DELETE_ZERO.getCode());
        try{
            iKongFuMapper.kongFuInsert(kongFu);
            return Result.successNotdatas(ResultCode.SUCCESS.getResult());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getResult()+e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public Result kongFuUpdate(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getKongfu_id())){
            return Result.fail("传入功夫ID为空");
        }
        String msg = VerificationUtil.verificationKongFu(kongFu);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        Map<String,Object> map = iKongFuMapper.kongFuIdQuery(kongFu);
        if(SetUtil.isMapNull(map)){
            return Result.fail("修改失败，该功夫不存在");
        }
        String name = SetUtil.toMapValueString(map,"name");
        if(!name.equals(kongFu.getName())){
            int count = iKongFuMapper.kongFuIsExist(kongFu);
            if(count > 0){
                return Result.fail("修改失败，功夫名称已存在");
            }
        }
        try{
            iKongFuMapper.kongFuUpdate(kongFu);
            return Result.successNotdatas(ResultCode.SUCCESS.getResult());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getResult()+e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public Result kongFuDelete(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getName())){
            return Result.fail("传入功夫名称为空");
        }
        int count = iKongFuMapper.kongFuIsExist(kongFu);
        if(count <= 0){
            return Result.fail("删除失败，该功夫不存在");
        }
        kongFu.setDeleteStatus(TableCode.Del.DELETE_ONE.getCode());
        try{
            iKongFuMapper.kongFuDelete(kongFu);
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
    public Result kongFuBatchDelete(Map<String,Object> map) {
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
                iKongFuMapper.kongFuBatchDelete(upMap);
            }
            return Result.successNotdatas(ResultCode.SUCCESS.getResult());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getResult()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getResult()+e.getMessage());
        }
    }

    /**
     * 查看详情
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public Result kongFuSee(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getName())){
            return Result.fail("传入功夫名称为空");
        }
        Map<String,Object> resultMap = iKongFuMapper.kongFuEditQuery(kongFu);
        String moveId = SetUtil.toMapValueString(resultMap,"kongfu_zhaoshi");
        if(StringUtil.isNull(moveId)){
            resultMap.put("moveName","");
        }else{
            resultMap.put("moveName",seeMoveName(moveId));
        }
        return Result.success(ResultCode.SUCCESS.getResult(),resultMap);
    }

    /**
     * 获取招式名称
     *
     * @param str str
     * @return  List<String>
     */
    private List<String> seeMoveName(String str){
        String[] values = str.split(",");
        List<String> list = new ArrayList<String>();
        for(String value : values){
            list.add(value);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("mIds",list);
        List<Map<String,Object>> resultList = iMoveMapper.moveQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(SetUtil.toMapValueString(map,"name"));
        }
        return nameList;
    }

    /**
     * 处理功夫类型
     *
     * @param type type
     * @return String
     */
    private String kongFuType(int type){
        switch (type){
            case 0:return TableCode.KongFu.TYPE_ZERO.getName();
            case 1:return TableCode.KongFu.TYPE_ONE.getName();
            case 2:return TableCode.KongFu.TYPE_TWO.getName();
            case 3:return TableCode.KongFu.TYPE_THREE.getName();
            case 4:return TableCode.KongFu.TYPE_FOUR.getName();
            case 5:return TableCode.KongFu.TYPE_FIVES.getName();
            case 6:return TableCode.KongFu.TYPE_SIX.getName();
            case 7:return TableCode.KongFu.TYPE_SEVEN.getName();
            default:return "其他";
        }
    }
}
