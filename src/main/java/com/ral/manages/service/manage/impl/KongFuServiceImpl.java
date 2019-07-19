package com.ral.manages.service.manage.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.entity.manage.KongFu;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.mapper.manage.IKongFuMapper;
import com.ral.manages.service.manage.IKongFuService;
import com.ral.manages.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class KongFuServiceImpl implements IKongFuService {

    @Autowired
    private IKongFuMapper iKongFuMapper;

    /**分页查询*/
    @Override
    public GeneralResponse kongFuPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageUtil.pageNum(map),PageUtil.pageSize(map));
        List<Map<String,Object>> kongFuList = iKongFuMapper.selectKongFuPagingQuery(map);
        return GeneralResponse.success("操作成功",PageUtil.resultPage(page.getTotal(),kongFuList));
    }

    /**新增*/
    @Override
    public GeneralResponse kongFuAdd(KongFu kongFu) {
        return null;
    }

    /**修改*/
    @Override
    public GeneralResponse kongFuUpdate(KongFu kongFu) {
        return null;
    }

    /**删除*/
    @Override
    public GeneralResponse kongFuDelete(KongFu kongFu) {
        return null;
    }
}
