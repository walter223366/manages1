package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.entity.app.Account;
import com.ral.manages.entity.Result;
import com.ral.manages.comms.verifi.VerificationParams;
import com.ral.manages.mapper.app.IAccountMapper;
import com.ral.manages.service.app.IAccountService;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.util.Base64Util;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import com.ral.manages.util.TimeUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements IAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private IAccountMapper iAccountMapper;

    /**
     * 分页查询
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result accountPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> accountList = iAccountMapper.accountPagingQuery(map);
        for(Map<String,Object> accountMap : accountList){
            int source = SetUtil.toMapValueInt(accountMap,"source");
            String source_value = (source==0? TableCode.User.SOURCE_ZERO.getName(): TableCode.User.SOURCE_ONE.getName());
            accountMap.put("source",source_value);
            int cancellation = SetUtil.toMapValueInt(accountMap,"cancellation");
            String cancellation_value = (cancellation==0? TableCode.User.CANCELLATION_ZERO.getName(): TableCode.User.CANCELLATION_ONE.getName());
            accountMap.put("cancellation",cancellation_value);
        }
        return Result.success(ResultCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),accountList));
    }

    /**
     * 编辑查询
     *
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public Result accountEditQuery(Account account) {
        if(StringUtil.isNull(account.getAccount())){
            return Result.fail("传入账号名称为空");
        }
        Map<String,Object> result = iAccountMapper.accountEditQuery(account);
        return Result.success(ResultCode.SUCCESS.getMsg(),result);
    }

    /**
     * 新增
     *
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public Result accountInsert(Account account) {
        String msg = VerificationParams.verificationAccount(account);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iAccountMapper.accountIsExist(account);
        if(count > 0){
            return Result.fail("新增失败，账号名称已存在");
        }
        account.setCancellation(TableCode.User.CANCELLATION_ZERO.getCode());
        account.setDeleteStatus(TableCode.Del.DELETE_ZERO.getCode());
        account.setLrrq(TimeUtil.currentTime());
        account.setId(StringUtil.getUUID());
        try{
            iAccountMapper.accountInsert(account);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public Result accountUpdate(Account account) {
        if(StringUtil.isNull(account.getId())){
            return Result.fail("传入账号ID为空");
        }
        String msg = VerificationParams.verificationAccount(account);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        Map<String,Object> map = iAccountMapper.accountIdQuery(account);
        if(SetUtil.isMapNull(map)){
            return Result.fail("修改失败，该账号不存在");
        }
        String name = SetUtil.toMapValueString(map,"account");
        if(!name.equals(account.getAccount())){
            int count = iAccountMapper.accountIsExist(account);
            if(count > 0){
                return Result.fail("修改失败，账号名称已存在");
            }
        }
        try{
            iAccountMapper.accountUpdate(account);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public Result accountDelete(Account account) {
        if(StringUtil.isNull(account.getAccount())){
            return Result.fail("传入账号名称为空");
        }
        int count = iAccountMapper.accountIsExist(account);
        if(count <= 0){
            return Result.fail("删除失败，该账号不存在");
        }
        account.setDeleteStatus(TableCode.Del.DELETE_ONE.getCode());
        try{
            iAccountMapper.accountDelete(account);
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
    public Result accountBatchDelete(Map<String,Object> map) {
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
                iAccountMapper.accountBatchDelete(upMap);
            }
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 登陆、注册
     *
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public Result accountSignUp(Account account) {
        String msg = VerificationParams.verificationAccount(account);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iAccountMapper.accountIsExist(account);
        if(count > 0){
            return Result.successNotdatas("登陆成功");
        }
        account.setCancellation(TableCode.User.CANCELLATION_ZERO.getCode());
        account.setDeleteStatus(TableCode.Del.DELETE_ZERO.getCode());
        account.setLrrq(TimeUtil.currentTime());
        account.setId(StringUtil.getUUID());
        try{
            iAccountMapper.accountInsert(account);
            return Result.successNotdatas("注册成功");
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }
}
