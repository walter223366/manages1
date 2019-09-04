package com.ral.manages.controller.wx;

import com.ral.manages.entity.SysConfigurers;
import com.ral.manages.util.ToolsUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *   微信授权
 *
 *   @author Double
 *   @since  2019-07-18
 */
@RestController
@RequestMapping("/weChat/")
public class WeChatUserInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(WeChatUserInfoController.class);
    @Autowired
    private SysConfigurers sys;

    //微信网页授权（静默授权）
    @GetMapping("silentRedirect")
    public void silentRedirect(HttpServletRequest request, HttpServletResponse response){
        String method = "getOpenId";
        String scope = sys.getWeChat_scopeBase();
        getAuthRedirect(response,method,scope);
    }

    //微信网页授权（用户授权）
    @GetMapping("userRedirect")
    public void userRedirect(HttpServletRequest request, HttpServletResponse response){
        String method = "userInfo";
        String scope = sys.getWeChat_scopeUserInfo();
        getAuthRedirect(response,method,scope);
    }

    //授权重定向
    private void getAuthRedirect(HttpServletResponse response,String method,String scope){
        String appId = sys.getWeChat_appId();
        String state = sys.getWeChat_state();//重定向后会带上的参数
        String codeUrl = sys.getWeChat_authorize(); //获取code地址
        String url = sys.getWeChat_callBack();
        url = url.concat(method);
        try {
            url = URLEncoder.encode(url,"utf-8");//地址编码
        } catch (UnsupportedEncodingException e) {
            LOG.error("重定向URL编码失败："+e.getMessage());
        }
        codeUrl = codeUrl.concat("?appid="+appId)
                .concat("&redirect_uri="+url)
                .concat("&response_type=code&scope="+scope)
                .concat("&state="+state)
                .concat("#wechat_redirect");
        System.out.println("重定向URL："+codeUrl);
        try {
            response.sendRedirect(codeUrl);
        } catch (IOException e) {
            LOG.error("RESPONSE重定向失败："+e.getMessage());
        }
    }

    //直接获取用户OpenId
    @GetMapping("getOpenId")
    public void getOpenId(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        JSONObject result = getOpenId(request,response,code);
        String url = sys.getWeb_url();
        url = url.concat("?openid="+result.optString("openid"));
        System.out.println("重定向前端页面ULR："+url);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOG.debug("重定向地址失败："+e.getMessage());
        }
    }

    //获取微信用户信息(用户授权)
    @GetMapping("userInfo")
    public void getAuthUserInfo(HttpServletRequest request,HttpServletResponse response){
        JSONObject result = new JSONObject();
        String code = request.getParameter("code");
        JSONObject json = getOpenId(request,response,code);
        String openId = json.optString("openid");
        String token = json.optString("access_token");
        String url = sys.getWehChat_userInfo();
        url = url.concat("?access_token="+token)
                .concat("&openid="+openId)
                .concat("&lang=zh_CN");
        try{
            String str = ToolsUtil.getRequest(url);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("获取OpenId失败："+e.getMessage());
        }
        String webUrl = sys.getWeb_url();
        webUrl = webUrl.concat("?openid="+result.optString("openid"));
        System.out.println("重定向前端页面ULR："+url);
        try {
            response.sendRedirect(webUrl);
        } catch (IOException e) {
            LOG.debug("重定向地址失败："+e.getMessage());
        }
    }

    //获取OpenId
    private JSONObject getOpenId(HttpServletRequest request,HttpServletResponse response,String code){
        JSONObject result = new JSONObject();
        //封装获取OpenId的微信API
        String tokenUrl = sys.getWeChat_accessToken();
        String appId = sys.getWeChat_appId();
        String secret = sys.getWeChat_appSecret();
        tokenUrl = tokenUrl.concat("?appid="+appId)
                .concat("&secret="+secret)
                .concat("&code="+code)
                .concat("&grant_type=authorization_code");
        try{
            String str = ToolsUtil.getRequest(tokenUrl);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("获取OpenId失败："+e.getMessage());
        }
        String openId = result.optString("openid");
        LOG.info("获取openId："+openId);
        request.getSession().setAttribute("OpenId", openId);
        return result;
    }

    //刷新token
    private JSONObject refreshToken(JSONObject json){
        JSONObject result = new JSONObject();
        String openId = json.optString("openid");
        String refreshToken = json.optString("refresh_token");
        String url = sys.getWeChat_refreshToken();
        url = url.concat("?appid="+openId)
                .concat("&grant_type=refresh_token")
                .concat("&refresh_token="+refreshToken);
        try{
            String str = ToolsUtil.getRequest(url);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("校验token失败："+e.getMessage());
        }
        return result;
    }

    //校验token是否有效
    private boolean checkTokenValid(JSONObject json){
        JSONObject result = new JSONObject();
        String token = json.optString("access_token");
        String openId = json.optString("openid");
        String url = sys.getWeChat_auth();
        url = url.concat("?access_token="+token)
                .concat("&openid="+openId);
        try{
            String str = ToolsUtil.getRequest(url);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("校验token失败："+e.getMessage());
        }
        String errcode = result.optString("errcode");
        if("0".equals(errcode)){
            return true;
        }else{
            return false;
        }
    }
}
