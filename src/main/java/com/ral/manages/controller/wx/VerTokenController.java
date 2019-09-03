package com.ral.manages.controller.wx;

import com.ral.manages.comms.exception.AesException;
import com.ral.manages.util.SHA1Util;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *   微信基础配置
 *
 *   @author Double
 *   @since  2019-07-18
 */
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
}
