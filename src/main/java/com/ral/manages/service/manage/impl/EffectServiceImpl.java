package com.ral.manages.service.manage.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.mapper.manage.IEffectMapper;
import com.ral.manages.service.manage.IEffectService;
import com.ral.manages.util.PageUtil;
import com.ral.manages.util.SetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class EffectServiceImpl implements IEffectService {

    private static final Logger LOG = LoggerFactory.getLogger(EffectServiceImpl.class);
    @Autowired
    private IEffectMapper iEffectMapper;

    /*查询*/
    @Override
    public GeneralResponse effectPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageUtil.pageNum(map),PageUtil.pageSize(map));
        List<Map<String,Object>> effectList = iEffectMapper.selectEffectPagingQuery(map);
        return GeneralResponse.success("操作成功",PageUtil.resultPage(page.getTotal(),SetUtil.clearValueNullToList(effectList)));
    }
}
