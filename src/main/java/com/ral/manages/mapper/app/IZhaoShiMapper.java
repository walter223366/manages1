package com.ral.manages.mapper.app;

import com.ral.manages.entity.app.ZhaoShi;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface IZhaoShiMapper {

    /*查询*/
    List<Map<String,Object>> selectZhaoShiPagingQuery(Map<String,Object> map);
    int selectZhaoShiToExist(@Param("zhaoshi") ZhaoShi zhaoShi);
    int selectZhaoShiToName(@Param("zhaoshi") ZhaoShi zhaoShi);
    /*新增*/
    void insertZhaoShi(@Param("zhaoshi") ZhaoShi zhaoShi);
    /*修改*/
    void updateZhaoShi(@Param("zhaoshi") ZhaoShi zhaoShi);
    /*删除*/
    void deleteZhaoShi(@Param("zhaoshi") ZhaoShi zhaoShi);
}
