package com.ral.manages.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ral.manages.comms.exception.AesException;
import com.ral.manages.comms.verifi.ProjectConst;
import com.ral.manages.entity.user.UserInfo;
import com.ral.manages.entity.user.WeixinBackInfo;
import com.ral.manages.util.SHA1Util;
import com.ral.manages.util.StringUtil;
import com.ral.manages.util.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VerTokenController {
    private static final Logger LOG = LoggerFactory.getLogger(VerTokenController.class);

    //微信基础配置校验服务器
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Object verToken(HttpServletRequest request, HttpServletResponse response){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if(StringUtil.isNull(signature) || StringUtil.isNull(timestamp) || StringUtil.isNull(nonce) || StringUtil.isNull(echostr)){
            try {
                response.sendRedirect("/manages/system/login");
            } catch (IOException e) { ;
                LOG.info(e.getMessage(), e);
            }
            return null;
        }else {
            System.out.println("signature：" + signature + "，echostr：" + echostr);
            String token = "aiurorigin";
            String result = "";
            try {
                result = SHA1Util.getSHA1(token, timestamp, nonce);
            } catch (AesException e) {
                LOG.info(e.getMessage(), e);
            }
            System.out.println("result:" + result);
            if (signature.equals(result)) {
                return echostr;
            } else {
                return false;
            }
        }
    }

    //获取OpenId
    @GetMapping("/authorization")
    public Object authMethod(String code){
        System.out.println("获取code:"+code);
        String value = "";
        String url = ProjectConst.GET_ACCESSTOKEN_URL;
        String appId = ProjectConst.APP_ID;
        url = url.replace("APPID",appId);
        String secret = ProjectConst.APP_SECRET;
        url = url.replace("SECRET",secret);
        url = url.replace("CODE",code);
        value = ToolsUtil.getRequest(url);
        WeixinBackInfo weixinBackInfo = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            weixinBackInfo = mapper.readValue(value,WeixinBackInfo.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOG.debug(e.getMessage(),e);
            Map<String,String> map = new HashMap<>();
            map.put("error","解密失败");
            return map;
        }
        System.out.println("获取OpenId:"+weixinBackInfo.getOpenid());
        return weixinBackInfo;
    }

    //获取微信用户信息
    public UserInfo getUserInfo(WeixinBackInfo backInfo) throws Exception {
        String url = ProjectConst.GET_USERINFO_URL;
        url = url.replace("ACCESS_TOKEN",backInfo.getAccess_token());
        url = url.replace("OPENID",backInfo.getOpenid());
        String result = ToolsUtil.getRequest(url);
        String str=new String(result.getBytes("ISO-8859-1"),"UTF-8");
        UserInfo userInfo = null;
        ObjectMapper mapper = new ObjectMapper();
        userInfo = mapper.readValue(str,UserInfo.class);
        return userInfo;
    }
}
