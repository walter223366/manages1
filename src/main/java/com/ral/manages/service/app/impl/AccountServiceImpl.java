package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.mapper.app.IBaseInfoMapper;
import com.ral.manages.service.app.IBaseInfoService;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.VerificationUtil;
import com.ral.manages.mapper.app.IAccountMapper;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("account")
public class AccountServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private IAccountMapper iAccountMapper;
    @Autowired
    private IBaseInfoMapper baseInfoMapper;
    @Autowired
    private IBaseInfoService baseInfoService;

    /**
     * 处理账号管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.PAGINGQUERY: result = accountPagingQuery(map);
                break;
            case ProjectConst.EDITQUERY: result = accountEditQuery(map);
                break;
            case ProjectConst.INSERT: result = accountInsert(map);
                break;
            case ProjectConst.UPDATE: result = accountUpdate(map);
                break;
            case ProjectConst.DELETE: result = accountDelete(map);
                break;
            case ProjectConst.BATCHDELETE: result = accountBatchDelete(map);
                break;
            case ProjectConst.SINGIN: result = accountSignIn(map);
                break;
            case ProjectConst.SIGNUP: result = accountSignUp(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> accountPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> accountList = iAccountMapper.accountPagingQuery(map);
        for(Map<String,Object> accountMap : accountList){
            int source = SetUtil.toMapValueInt(accountMap,"source");
            String source_value = (source==0? TableCode.SOURCE_ZERO.getName(): TableCode.SOURCE_ONE.getName());
            accountMap.put("source",source_value);
            int cancellation = SetUtil.toMapValueInt(accountMap,"cancellation");
            String cancellation_value = (cancellation==0? TableCode.CANCELLATION_ZERO.getName(): TableCode.CANCELLATION_ONE.getName());
            accountMap.put("cancellation",cancellation_value);
        }
        return PageBean.resultPage(page.getTotal(),accountList);
    }

    /*编辑查询*/
    private Map<String,Object> accountEditQuery(Map<String,Object> map) {
        String account = MapUtil.getString(map,"account");
        if(StringUtil.isNull(account)){
            throw new BizException("传入账号名称为空");
        }
        return iAccountMapper.accountEditQuery(map);
    }

    /*新增*/
    private Map<String,Object> accountInsert(Map<String,Object> map) {
        VerificationUtil.verificationAccount(map);
        int count = iAccountMapper.accountIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，账号名称已存在");
        }
        map.put("cancellation",TableCode.CANCELLATION_ZERO.getCode());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        map.put("lrrq",TimeUtil.currentTime());
        map.put("id",StringUtil.getUUID());
        try{
            iAccountMapper.accountInsert(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> accountUpdate(Map<String,Object> map) {
        String id = MapUtil.getString(map,"id");
        if(StringUtil.isNull(id)){
            throw new BizException("传入账号ID为空");
        }
        VerificationUtil.verificationAccount(map);
        Map<String,Object> queryMap = iAccountMapper.accountIdQuery(map);
        if(SetUtil.isMapNull(queryMap)){
            throw new BizException("修改失败，该账号不存在");
        }
        String name = MapUtil.getString(queryMap,"account");
        String account = MapUtil.getString(map,"account");
        if(!name.equals(account)){
            int count = iAccountMapper.accountIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，账号名称已存在");
            }
        }
        try{
            iAccountMapper.accountUpdate(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> accountDelete(Map<String,Object> map) {
        String account = MapUtil.getString(map,"account");
        if(StringUtil.isNull(account)){
            throw new BizException("传入账号名称为空");
        }
        int count = iAccountMapper.accountIsExist(map);
        if(count <= 0){
            throw new BizException("删除失败，该账号不存在");
        }
        map.put("deleteStatus",TableCode.DELETE_ONE.getCode());
        try{
            iAccountMapper.accountDelete(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*批量删除*/
    private Map<String,Object> accountBatchDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                iAccountMapper.accountDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("批量删除失败："+e.getMessage());
            throw new BizException("批量删除失败："+e.getMessage());
        }
    }

    /*登陆、未注册返回状态*/
    private Map<String,Object> accountSignIn(Map<String,Object> map) {
        VerificationUtil.verificationAccount(map);
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> qMap = iAccountMapper.accountEditQuery(map);
        if(!SetUtil.isMapNull(qMap)){
            result = landed(qMap);
        }else {
            result = registered(map);
        }
        return result;
    }

    /*注册*/
    private Map<String,Object> accountSignUp(Map<String,Object> map){
        String account = MapUtil.getString(map,"account");
        if(StringUtil.isNull(account)){
            throw new BizException("传入账号ID为空");
        }
        String nickname = MapUtil.getString(map,"nickname");
        if(StringUtil.isNull(nickname)){
            throw new BizException("传入人物呢称为空");
        }
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("account",account);
        userMap.put("cancellation", TableCode.CANCELLATION_ZERO.getCode());
        userMap.put("deleteStatus", TableCode.DELETE_ZERO.getCode());
        userMap.put("source",TableCode.SOURCE_ZERO.getCode());
        userMap.put("lrrq", TimeUtil.currentTime());
        String id = StringUtil.getUUID();
        userMap.put("id",id);

        map.put("last_time",TableCode.LASTTIME_ONE.getCode());
        map.put("enable",TableCode.ENABLE_ONE.getCode());
        map.put("id",StringUtil.getUUID());
        map.put("user_id",id);
        map.put("cancellation", TableCode.CANCELLATION_ZERO.getCode());
        try {
            iAccountMapper.accountInsert(userMap);
            baseInfoMapper.baseInfoInsert(map);
        } catch (Exception e) {
            log.debug("注册失败：" + e.getMessage());
            throw new BizException("注册失败：" + e.getMessage());
        }
        return new HashMap<>();
    }

    /*已有账号时，查询人物信息*/
    private Map<String,Object> landed(Map<String,Object> map){
        Map<String,Object> infoMap = SetUtil.clearValueNullToMap(map);
        Map<String,Object> qMap = baseInfoService.baseInfo(infoMap);
        map.put("isType",TableCode.LANDED.getCode());//登陆状态
        map.put("character",qMap);//角色信息
        return map;
    }

    /*未有账号时，注册并查询初始化人物信息*/
    private Map<String,Object> registered(Map<String,Object> map){
        Map<String,Object> infoMap = SetUtil.clearValueNullToMap(map);
        Map<String,Object> qMap = new HashMap<String,Object>();
        map.put("isType",TableCode.REGISTERED.getCode());//注册状态
        map.put("character",qMap);//角色信息（初始化角色为空）
        return map;
    }

    /*切换人物*/
    private Map<String,Object> switchBaseInfo(Map<String,Object> map){
        String account = MapUtil.getString(map,"account");
        if(StringUtil.isNull(account)){
            throw new BizException("传入账号ID为空");
        }
        Map<String,Object> qMap = iAccountMapper.accountEditQuery(map);
        if(SetUtil.isMapNull(qMap)){
            throw new BizException("该账号不存在");
        }
        //TODO
        return null;
    }
}
