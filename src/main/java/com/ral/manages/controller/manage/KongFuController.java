package com.ral.manages.controller.manage;

import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.service.manage.IKongFuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/kongFu")
public class KongFuController {

    private static final Logger LOG = LoggerFactory.getLogger(KongFuController.class);
    @Autowired
    private IKongFuService iKongFuService;

    /**查询*/
    @PostMapping("/pagingQuery")
    public Object kongFuPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }


}
