package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.emun.StateTable;
import com.ral.manages.entity.app.KongFu;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verifi.VerificationParams;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import com.ral.manages.service.app.IKongFuService;
import com.ral.manages.commom.page.PageBean;
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
    public GeneralResponse kongFuPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> kongFuList = iKongFuMapper.kongFuPagingQuery(map);
        for(Map<String,Object> kongFuMap : kongFuList){
            kongFuMap.put("type",kongFuType(SetUtil.toMapValueInt(kongFuMap,"type")));
            int enable = SetUtil.toMapValueInt(kongFuMap,"enable");
            String enableValue = (enable==StateTable.KongFu.ENABLE_ONE.getCode()?StateTable.KongFu.ENABLE_ONE.getName():StateTable.KongFu.ENABLE_ZERO.getName());
            kongFuMap.put("enable",enableValue);
        }
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),kongFuList));
    }

    /**
     * 编辑查询
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse kongFuEditQuery(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getName())){
            return GeneralResponse.fail("传入功夫名称为空");
        }
        Map<String,Object> result = iKongFuMapper.kongFuEditQuery(kongFu);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),result);
    }

    /**
     * 招式下拉框
     *
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse kongFuAddMove() {
        List<Map<String,Object>> kongFuList = iMoveMapper.moveQueryMarquee();
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("data",kongFuList);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),result);
    }

    /**
     * 新增
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse kongFuInsert(KongFu kongFu) {
        String msg = VerificationParams.verificationKongFu(kongFu);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iKongFuMapper.kongFuIsExist(kongFu);
        if(count > 0){
            return GeneralResponse.fail("新增失败，功夫名称已存在");
        }
        kongFu.setKongfu_id(StringUtil.getUUID());
        kongFu.setDeleteStatus(StateTable.Del.DELETE_ZERO.getCode());
        try{
            iKongFuMapper.kongFuInsert(kongFu);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse kongFuUpdate(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getKongfu_id())){
            return GeneralResponse.fail("传入功夫ID为空");
        }
        String msg = VerificationParams.verificationKongFu(kongFu);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        Map<String,Object> map = iKongFuMapper.kongFuIdQuery(kongFu);
        if(SetUtil.isMapNull(map)){
            return GeneralResponse.fail("修改失败，该功夫不存在");
        }
        String name = SetUtil.toMapValueString(map,"name");
        if(!name.equals(kongFu.getName())){
            int count = iKongFuMapper.kongFuIsExist(kongFu);
            if(count > 0){
                return GeneralResponse.fail("修改失败，功夫名称已存在");
            }
        }
        try{
            iKongFuMapper.kongFuUpdate(kongFu);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse kongFuDelete(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getName())){
            return GeneralResponse.fail("传入功夫名称为空");
        }
        int count = iKongFuMapper.kongFuIsExist(kongFu);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该功夫不存在");
        }
        kongFu.setDeleteStatus(StateTable.Del.DELETE_ONE.getCode());
        try{
            iKongFuMapper.kongFuDelete(kongFu);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 批量删除
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse kongFuBatchDelete(Map<String,Object> map) {
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
                upMap.put("deleteStatus",StateTable.Del.DELETE_ONE.getCode());
                iKongFuMapper.kongFuBatchDelete(upMap);
            }
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 查看详情
     *
     * @param kongFu kongFu
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse kongFuSee(KongFu kongFu) {
        if(StringUtil.isNull(kongFu.getName())){
            return GeneralResponse.fail("传入功夫名称为空");
        }
        Map<String,Object> resultMap = iKongFuMapper.kongFuEditQuery(kongFu);
        String moveId = SetUtil.toMapValueString(resultMap,"kongfu_zhaoshi");
        if(StringUtil.isNull(moveId)){
            resultMap.put("moveName","");
        }else{
            resultMap.put("moveName",seeMoveName(moveId));
        }
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),resultMap);
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
