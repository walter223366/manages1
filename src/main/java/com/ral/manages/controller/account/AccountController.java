package com.ral.manages.controller.account;

import com.ral.manages.emun.CloudResponseCode;
import com.ral.manages.entity.account.AccountEntity;
import com.ral.manages.exception.BizException;
import com.ral.manages.exception.CloudResponse;
import com.ral.manages.service.account.IAccountService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Scope("prototype")
@RequestMapping("/manages/user")
public class AccountController {

    private static final Logger LOG = Logger.getLogger(AccountController.class);
    @Autowired
    private IAccountService iAccountService;

    //index登录路径
    @RequestMapping(value = {"/", ""})
    public String view(ModelMap map) {
        return "index.html";
    }

    @RequestMapping("/userLoad")
    public Object userLoad(@RequestBody @Validated AccountEntity accountEntity) {
        LOG.info("请求参数："+accountEntity.toString());
        CloudResponse cloudResponse = new CloudResponse();
        try {
            Map<String,Object> map = iAccountService.loadAccountInfo(accountEntity);
            JSONObject result = JSONObject.fromObject(map);
            cloudResponse.setReqid("");
            cloudResponse.setRows(result.toString());
        } catch (BizException ex) {
            LOG.debug("操作失败", ex);
            cloudResponse.setCode(CloudResponseCode.BIZ_EXCEPTION);
            cloudResponse.setMsg(ex.getMessage());
        } catch (Exception e) {
            LOG.error("系统错误", e);
            cloudResponse.setCode(CloudResponseCode.UNKNOW_EXCEPTION);
            cloudResponse.setMsg(e.getMessage());
        }
        return cloudResponse;
    }


}

