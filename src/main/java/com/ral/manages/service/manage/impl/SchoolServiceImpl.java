package com.ral.manages.service.manage.impl;

import com.ral.manages.entity.manage.School;
import com.ral.manages.mapper.manage.ISchoolMapper;
import com.ral.manages.service.manage.ISchoolService;
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
    public String pageCheckSchoolInfo(School school) {

        return null;
    }

    @Override
    public void insertSchoolInfo(School school) {

    }

    @Override
    public void updateSchoolInfo(School school) {

    }

    @Override
    public void deleteSchoolInfo(School school) {

    }
}
