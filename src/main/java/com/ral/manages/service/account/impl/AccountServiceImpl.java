package com.ral.manages.service.account.impl;

import com.ral.manages.emun.StateTable;
import com.ral.manages.entity.account.AccountEntity;
import com.ral.manages.exception.BizException;
import com.ral.manages.mapper.account.IAccountMapper;
import com.ral.manages.service.account.IAccountService;
import com.ral.manages.util.ParamsUtil;
import com.ral.manages.util.StringUtil;
import com.ral.manages.util.TimeUtil;
import com.ral.manages.util.page.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements IAccountService, StateTable {

    @Autowired
    private AccountEntity accountEntity;
    @Autowired
    private IAccountMapper iAccountMapper;

    /*登录账号*/
    @Override
    public Map<String,Object> loadAccountInfo(AccountEntity accountInfo) {
        accountEntity = iAccountMapper.selectAccountInfo(accountInfo);
        if(accountEntity == null){
            throw new BizException("登录失败，该账号不存在");
        }
        String passworld = accountInfo.getPass();
        if(!passworld.equals(accountEntity.getPass())){
            throw new BizException("登录失败，账号或密码有误");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type","0");
        return map;
    }

    /**查询账号*/
    @Override
    public PageBean<?> pageCheckAccountInfo(Map<String,Object> map) {

        return null;
    }

    /**新增账号*/
    @Override
    public int newAccountInfo(Map<String,Object> map) {
        accountEntity.setId(StringUtil.getUUID());
        accountEntity.setAccount(ParamsUtil.toMapGetValue(map,"account"));
        accountEntity.setPass(ParamsUtil.toMapGetValue(map,"pass"));
        accountEntity.setSource(USER_SOURCE);
        accountEntity.setLrrq(TimeUtil.currentTime());
        accountEntity.setCancellation(CANCELLATION_ZERO);
        accountEntity.setTellphone(12);
        return iAccountMapper.insertAccountInfo(accountEntity);
    }

    /**注销账号*/
    @Override
    public int logoutAccountInfo(Map<String,Object> map) {
        return 0;
    }

    /**修改账号*/
    @Override
    public int modifyAccountInfo(Map<String,Object> map) {
        return 0;
    }
}

