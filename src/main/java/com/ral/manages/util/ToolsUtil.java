package com.ral.manages.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class ToolsUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ToolsUtil.class);
    private static HttpClient client;
    private static GetMethod getMethod;
    static{
        client = new HttpClient();
    }

    public static String getRequest(String url){
        getMethod = new GetMethod(url);
        String result = "";
        try {
            int status = client.executeMethod(getMethod);
            if (status == HttpStatus.SC_OK) {// HTTP 200 OK
                result = getMethod.getResponseBodyAsString();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            LOG.debug(e.getMessage(),e);
        }
        return result;
    }
}
