package com.ral.manages.mapper.app;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IAccountMapper {

    /*查询*/
    List<Map<String,Object>> accountPagingQuery(Map<String,Object> map);
    int accountIsExist(Map<String,Object> map);
    Map<String,Object> accountEditQuery(Map<String,Object> map);
    Map<String,Object> accountIdQuery(Map<String,Object> map);
    Map<String,Object> accountQueryName(@Param("userId") String userId);
    List<Map<String,Object>> accountQueryMarquee();
    /*新增*/
    void accountInsert(Map<String,Object> map);
    /*修改*/
    void accountUpdate(Map<String,Object> map);
    /*删除*/
    void accountDelete(Map<String,Object> map);
}
