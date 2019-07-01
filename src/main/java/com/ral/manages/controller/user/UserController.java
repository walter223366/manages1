package com.ral.manages.controller.user;

import com.ral.manages.check.CheckParams;
import com.ral.manages.emun.CloudResponseCode;
import com.ral.manages.entity.user.User;
import com.ral.manages.exception.BizException;
import com.ral.manages.exception.CloudResponse;
import com.ral.manages.exception.Result;
import com.ral.manages.service.user.IUserService;
import com.ral.manages.util.Base64Util;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/manages/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService iUserService;

    @GetMapping("/ai")
    public String user(){
        return "hello ";
    }

    /**用户登录*/
    @RequestMapping("/userLoad")
    public Object userLoad(User user) {
        LOG.info("userLoad method -> 参数：" + user);
        CloudResponse cloudResponse = new CloudResponse();
        try {
            Result result = iUserService.loadUserInfo(user);
            JSONObject resultJson = JSONObject.fromObject(result);
            cloudResponse.setRows(Base64Util.Base64Encode(resultJson.toString()));
            cloudResponse.setReqid("");
            LOG.info("userLoad method -> 返回值：" + resultJson);
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

    /**用户新增*/
    @RequestMapping("/newUser")
    public Object newUser(User user){
        LOG.info("newUser method -> 参数：" + user);
        CloudResponse cloudResponse = new CloudResponse();
        try {
            iUserService.newUserInfo(user);
            cloudResponse.setRows(Base64Util.Base64Encode(new JSONObject().toString()));
            cloudResponse.setReqid("");
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

    /**用户修改*/
    @RequestMapping("/alterUser")
    public Object alterUser(User user){
        LOG.info("alterUser method -> 参数：" + user);
        CloudResponse cloudResponse = new CloudResponse();
        try {
            Result result = iUserService.updateUserInfo(user);
            JSONObject resultJson = JSONObject.fromObject(result);
            cloudResponse.setRows(Base64Util.Base64Encode(resultJson.toString()));
            cloudResponse.setReqid("");
            LOG.info("alterUser method -> 返回值：" + resultJson);
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

    /**用户注销*/
    @RequestMapping("/cancetUser")
    public Object cancetUser(User user){
        LOG.info("cancetUser method -> 参数：" + user);
        CloudResponse cloudResponse = new CloudResponse();
        try {
            Result result = iUserService.updataUserToCancet(user);
            JSONObject resultJson = JSONObject.fromObject(result);
            cloudResponse.setRows(Base64Util.Base64Encode(resultJson.toString()));
            cloudResponse.setReqid("");
            LOG.info("cancetUser method -> 返回值：" + resultJson);
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
