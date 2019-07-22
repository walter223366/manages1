package com.ral.manages.mapper.manage;

import com.ral.manages.entity.manage.Effect;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IEffectMapper {

    /*查询*/
    List<Map<String,Object>> selectEffectPagingQuery(Map<String,Object> map);
    int selectEffectToExist(@Param("effect")Effect effect);
    /*新增*/
    void insertEffect(@Param("effect")Effect effect);
    /*修改*/
    void updateEffect(@Param("effect")Effect effect);
    /*删除*/
    void deleteEffect(@Param("effect")Effect effect);
}
