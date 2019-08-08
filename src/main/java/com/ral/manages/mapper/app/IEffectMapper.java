package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.Effect;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IEffectMapper {

    /*查询*/
    List<Map<String,Object>> effectPagingQuery(Map<String,Object> map);
    int effectIsExist(@Param("effect") Effect effect);
    Map<String,Object> effectEditQuery(@Param("effect") Effect effect);
    /*新增*/
    void effectInsert(@Param("effect") Effect effect);
    /*修改*/
    void effectUpdate(@Param("effect") Effect effect);
    /*删除*/
    void effectDelete(@Param("effect") Effect effect);
    /*招式管理下拉菜单*/
    List<Map<String,Object>> effectQueryMarquee();
}
