package com.ral.manages.service.app.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.mapper.app.IEffectMapper;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import com.ral.manages.mapper.app.ISchoolMapper;
import com.ral.manages.service.app.UnifiedCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("downBox")
public class DownBoxServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(DownBoxServiceImpl.class);
    @Autowired
    private IEffectMapper effectMapper;
    @Autowired
    private IKongFuMapper kongFuMapper;
    @Autowired
    private ISchoolMapper schoolMapper;
    @Autowired
    private IMoveMapper moveMapper;


    /**
     * 下拉框处理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.MOVEDOWNBOX: result = moveDownBox();
                break;
            case ProjectConst.EFFECTDOWNBOX: result = effectDownBox();
                break;
            case ProjectConst.KONGFUDOWNBOX: result = kongFuDownBox();
                break;
            case ProjectConst.SCHOOLDOWNBOX: result = schoolDownBox();
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*招式下拉框*/
    private Map<String,Object> moveDownBox() {
        List<Map<String,Object>> resultList = moveMapper.moveQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*效果下拉框*/
    private Map<String,Object> effectDownBox() {
        List<Map<String,Object>> resultList = effectMapper.effectQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*功夫下拉框*/
    private Map<String,Object> kongFuDownBox() {
        List<Map<String,Object>> resultList = kongFuMapper.kongFuQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*门派下拉框*/
    private Map<String,Object> schoolDownBox(){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> resultList = schoolMapper.schoolQueryMarquee();
        resultMap.put("data",resultList);
        return resultMap;
    }
}
