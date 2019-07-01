package com.ral.manages.service.manage.impl;

import com.ral.manages.entity.manage.School;
import com.ral.manages.mapper.manage.ISchoolMapper;
import com.ral.manages.service.manage.ISchoolService;
import com.ral.manages.util.page.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements ISchoolService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolServiceImpl.class);
    @Autowired
    private ISchoolMapper iSchoolMapper;

    @Override
    public PageBean<?> pageCheckSchoolInfo(School school) {
        int current = school.getCurrent();
        int limit = school.getLimit();
        PageBean<?> page = new PageBean<>(limit, current);
        if (current != 0 && limit != 0) {

        }
        return null;
    }
}
