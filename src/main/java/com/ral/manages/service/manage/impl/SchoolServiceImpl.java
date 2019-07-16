package com.ral.manages.service.manage.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.mapper.manage.ISchoolMapper;
import com.ral.manages.service.manage.ISchoolService;
import com.ral.manages.util.SetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolServiceImpl implements ISchoolService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolServiceImpl.class);

    @Autowired
    private ISchoolMapper iSchoolMapper;

    /**分页查询*/
    @Override
    public GeneralResponse schoolPagingQuery(Map<String,Object> map) {
        GeneralResponse generalResponse = new GeneralResponse();
        int pageNum = SetUtil.toMapValueInt(map,"pageNum");
        int pageSize = SetUtil.toMapValueInt(map,"pageSize");
        pageNum = (pageNum==0?1:pageNum);
        pageSize = (pageSize==0?10:pageSize);
        Page<Map<String,Object>> page = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> schoolList = iSchoolMapper.selectSchoolPagingQuery(map);
        Map<String,Object> result = new HashMap<>();
        result.put("datas",schoolList);
        result.put("total",page.getTotal());
        return generalResponse.success("操作成功",result);
    }
}
