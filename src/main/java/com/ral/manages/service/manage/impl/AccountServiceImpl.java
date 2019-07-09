package com.ral.manages.service.manage.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.entity.manage.Account;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.mapper.manage.IAccountMapper;
import com.ral.manages.service.manage.IAccountService;
import com.ral.manages.util.SetUtil;
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
    @Autowired
    private GeneralResponse generalResponse;

    @Override
    public GeneralResponse selectAccountToPage(Map<String,Object> map) {
        int pageNum = SetUtil.toMapValueInt(map,"pageNum");
        int pageSize = SetUtil.toMapValueInt(map,"pageSize");
        pageNum = (pageNum==0?1:pageNum);
        pageSize = (pageSize==0?1:1);
        Page<Map<String,Object>> page = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> accountList = iAccountMapper.selectAccountToPage();
        System.out.println("总共条数："+page.getTotal());
        return generalResponse.success("操作成功",accountList);
    }


}
