package com.ral.manages.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class HttpSendUtil {

    private static final String charset = "UTF-8";//字符编码集
    private static final Log logger = LogFactory.getLog(HttpSendUtil.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 8000;//设置超时时间

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    public static String doPostJSON(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(),charset);
            stringEntity.setContentEncoding(charset);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            logger.debug(response.getStatusLine().getStatusCode());
            httpStr = EntityUtils.toString(entity,charset);
        } catch (IOException e) {
            logger.debug(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.debug(e.getMessage(),e);
                }
            }
        }
        return httpStr;
    }

    public static String doGet(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
            return httpStr;
        } catch (IOException e) {
            logger.debug(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ie) {
                    throw new RuntimeException(ie.getMessage(),ie);
                }
            }
        }
    }

    public static void doRedirect(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("ContentType","application/json");
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            logger.debug(response.getStatusLine().getStatusCode());
            response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            logger.debug(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    logger.debug(e.getMessage(),e);
                }
            }
        }
    }
}
