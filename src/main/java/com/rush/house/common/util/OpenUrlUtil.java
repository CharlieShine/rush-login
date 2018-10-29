package com.rush.house.common.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class OpenUrlUtil {

    private static final Logger log = LoggerFactory.getLogger(OpenUrlUtil.class);

    public static String openPostUrl(String url, Map<String, String> map) {
        try {
            PostMethod method = new PostMethod(url);
            Iterator<String> it = map.keySet().iterator();
            String key;
            HttpMethodParams hp = new HttpMethodParams();
            hp.setSoTimeout(300000);
            hp.setContentCharset("UTF-8");
            hp.setUriCharset("UTF-8");
            NameValuePair[] data = new NameValuePair[map.size()];
            int i = 0;
            while (it.hasNext()) {
                key = it.next();
                data[i] = new NameValuePair(key, map.get(key));
                i++;
            }
            method.setRequestBody(data);
            method.setParams(hp);
            HttpClient httpclient = new HttpClient();
            httpclient.executeMethod(method);
            String result = new String(method.getResponseBody(), "utf-8");
            return result;
        } catch (Exception e) {
            log.error("发送post请求失败, url:" + url, e);
        } finally {

        }
        return null;
    }

    public static String openPostUrlWithCookies(String url, Map<String, String> map, String cookies) {
        try {
            PostMethod method = new PostMethod(url);
            Iterator<String> it = map.keySet().iterator();
            String key;
            HttpMethodParams hp = new HttpMethodParams();
            hp.setSoTimeout(300000);
            hp.setContentCharset("UTF-8");
            hp.setUriCharset("UTF-8");
            NameValuePair[] data = new NameValuePair[map.size()];
            int i = 0;
            while (it.hasNext()) {
                key = it.next();
                data[i] = new NameValuePair(key, map.get(key));
                i++;
            }
            method.setRequestBody(data);
            method.setParams(hp);
            method.addRequestHeader("Cookie", cookies);
            HttpClient httpclient = new HttpClient();
            httpclient.executeMethod(method);
            String result = new String(method.getResponseBody(), "utf-8");
            return result;
        } catch (Exception e) {
            log.error("发送post请求失败, url:" + url, e);
        } finally {

        }
        return null;
    }

    public static String openGetUrl(String url, String cookies) {
        try {
            HttpMethod method = new GetMethod(url);
            HttpClient httpclient = new HttpClient();
            HttpMethodParams hp = new HttpMethodParams();
            hp.setSoTimeout(300000);
            hp.setContentCharset("UTF-8");
            hp.setUriCharset("UTF-8");
            method.setParams(hp);
            if (cookies != null) {
                method.addRequestHeader("Cookie", cookies);
            }
            httpclient.executeMethod(method);
            String result = new String(method.getResponseBody(), "utf-8");
            return result;
        } catch (Exception e) {
            log.error("发送get请求失败, url:" + url, e);
        }
        return null;
    }

    /**
     * 大文件上传(流的方式)
     * @param url
     * @param filePath
     * @param filedName 上传接口文件流字段名
     * @return
     */
    public static String uploadFileByStream (String url, String filePath, String filedName, String cookie) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            File file = new File(filePath);
            /*out = new ByteArrayOutputStream();// byte[]的方式
            in = new FileInputStream(file);
            byte buffer [] = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.toByteArray();*/
            String fileName = filePath.replaceAll(".*\\\\", "");
            FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY, fileName);
//            builder.addPart("filename", fileBody);
//            builder.addBinaryBody("filename", new FileInputStream(file), ContentType.DEFAULT_BINARY, fileName);
            HttpEntity entity = builder.build();
//            post.addHeader("cookie", cookie);
            post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
            post.setEntity(entity);
            // 设置超时
            RequestConfig.Builder requestBuilder = RequestConfig.custom();
            requestBuilder.setConnectTimeout(60000);// 连接超时 - 1分钟
            requestBuilder.setSocketTimeout(180000);// 读取超时 - 3分钟
//            requestBuilder.setConnectionRequestTimeout(9000);//
            post.setConfig(requestBuilder.build());
            // 开始上传
            response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            log.error("大文件上传(流的方式)失败,文件名:" + filePath, e);
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(httpClient);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return result;
    }

    /**
     * 转换cookie字符串
     * @param cookies
     * @return
     */
    public static String getCookiesStr (Cookie[] cookies) {
        StringBuilder sb = new StringBuilder("");
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
            }
        }
        return sb.toString();
    }

}
