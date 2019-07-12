package com.ral.manages.service.manage.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.emun.StateTable;
import com.ral.manages.entity.manage.Account;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.exception.VerificationParams;
import com.ral.manages.mapper.manage.IAccountMapper;
import com.ral.manages.service.manage.IAccountService;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import com.ral.manages.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements IAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private IAccountMapper iAccountMapper;

    /**分页查询*/
    @Override
    public GeneralResponse pagingQuery(Map<String,Object> map) {
        GeneralResponse generalResponse = new GeneralResponse();
        int pageNum = SetUtil.toMapValueInt(map,"pageNum");
        int pageSize = SetUtil.toMapValueInt(map,"pageSize");
        pageNum = (pageNum==0?1:pageNum);
        pageSize = (pageSize==0?10:pageSize);
        Page<Map<String,Object>> page = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> accountList = iAccountMapper.selectAccountPagingQuery(map);
        for(Map<String,Object> accountMap : accountList){
            int source = SetUtil.toMapValueInt(accountMap,"source");
            accountMap.put("source",source==1?"微信":"其他");
            int cancellation = SetUtil.toMapValueInt(accountMap,"cancellation");
            accountMap.put("cancellation",cancellation==1?"注销":"正常");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("datas",accountList);
        result.put("total",page.getTotal());
        return generalResponse.success("操作成功",result);
    }

    /**新增*/
    @Override
    public GeneralResponse accountAdd(Account account) {
        String msg = VerificationParams.verification_Account(account);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iAccountMapper.selectAccountToExist(account);
        if(count > 0){
            return GeneralResponse.fail("新增失败，该账号已存在");
        }
        int cancellation = (int) StateTable.CANCELLATION_ZERO.getCode();
        account.setCancellation(cancellation);
        account.setLrrq(TimeUtil.currentTime());
        account.setId(StringUtil.getUUID());
        try{
            iAccountMapper.insertAccount(account);
            return GeneralResponse.successNotdatas("新增成功");
        }catch (Exception e){
            LOG.debug("新增失败，"+e.getMessage(),e);
            return GeneralResponse.fail("新增失败，"+e.getMessage());
        }
    }

    /**修改*/
    @Override
    public GeneralResponse accountUpdate(Account account) {
        int count = iAccountMapper.selectAccountToExist(account);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该账号不存在");
        }
        try{
            iAccountMapper.updateAccount(account);
            return GeneralResponse.successNotdatas("修改成功");
        }catch (Exception e){
            LOG.debug("修改失败，"+e.getMessage(),e);
            return GeneralResponse.fail("修改失败，"+e.getMessage());
        }
    }

    /**删除*/
    @Override
    public GeneralResponse accountDelete(Account account) {
        int count = iAccountMapper.selectAccountToExist(account);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该账号不存在");
        }
        int cancellation = (int) StateTable.CANCELLATION_ZERO.getCode();
        account.setCancellation(cancellation);
        try{
            iAccountMapper.deleteAccount(account);
            return GeneralResponse.successNotdatas("删除成功");
        }catch (Exception e){
            LOG.debug("删除失败，"+e.getMessage(),e);
            return GeneralResponse.fail("删除失败，"+e.getMessage());
        }
    }
}
