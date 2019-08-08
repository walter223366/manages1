package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.emun.StateTable;
import com.ral.manages.entity.app.Account;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.mapper.app.IAccountMapper;
import com.ral.manages.service.app.IAccountService;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import com.ral.manages.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements IAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private IAccountMapper iAccountMapper;

    /**
     * 分页查询
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse accountPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> accountList = iAccountMapper.accountPagingQuery(map);
        for(Map<String,Object> accountMap : accountList){
            int source = SetUtil.toMapValueInt(accountMap,"source");
            String source_value = (source==0?StateTable.User.SOURCE_ZERO.getName():StateTable.User.SOURCE_ONE.getName());
            accountMap.put("source",source_value);
            int cancellation = SetUtil.toMapValueInt(accountMap,"cancellation");
            String cancellation_value = (cancellation==0?StateTable.User.CANCELLATION_ZERO.getName():StateTable.User.CANCELLATION_ONE.getName());
            accountMap.put("cancellation",cancellation_value);
        }
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),accountList));
    }

    /**
     * 编辑查询
     * @param account id or account
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse accountEditQuery(Account account) {
        if(StringUtil.isNull(account.getAccount()) || StringUtil.isNull(account.getId())){
            return GeneralResponse.fail("账号名称或账号ID为空");
        }
        Map<String,Object> result = iAccountMapper.accountEditQuery(account);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),result);
    }

    /**
     * 新增
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse accountInsert(Account account) {
        String msg = VerificationParams.verificationAccount(account);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iAccountMapper.accountIsExist(account);
        if(count > 0){
            return GeneralResponse.fail("新增失败，该账号已存在");
        }
        account.setCancellation(StateTable.User.CANCELLATION_ZERO.getCode());
        account.setLrrq(TimeUtil.currentTime());
        account.setId(StringUtil.getUUID());
        try{
            iAccountMapper.accountInsert(account);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse accountUpdate(Account account) {
        String msg = VerificationParams.verificationAccount(account);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iAccountMapper.accountIsExist(account);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该账号不存在");
        }
        try{
            iAccountMapper.accountUpdate(account);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     * @param account account
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse accountDelete(Account account) {
        int count = iAccountMapper.accountIsExist(account);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该账号不存在");
        }
        account.setCancellation(StateTable.User.CANCELLATION_ONE.getCode());
        try{
            iAccountMapper.accountDelete(account);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }
}
