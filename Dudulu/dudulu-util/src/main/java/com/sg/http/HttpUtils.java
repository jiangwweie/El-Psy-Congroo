/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 */
public class HttpUtils {

//    private static final Logger LOG = Logger.getLogger(HttpUtils.class);

    public static String doPost(JSONObject json, String url) throws IOException, ParseException {
        if (json == null) {
            return null;
        }
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                20000);

        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("method", "POST");

        try {
            httpPost.setEntity(new StringEntity(json.toString(), "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.err.println(result);
            return result;
        } catch (IOException | ParseException ex) {
//            LOG.error("do post of the url:" + url + " occur error.", ex);
            throw ex;
        }
    }

    public static String doPost1(String url, List<NameValuePair> nvps) throws IOException, ParseException {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                30000);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("method", "POST");
        httpPost.addHeader("accept", "application/json");

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps);
            entity.setContentType("x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.err.println(result);
            return result;
        } catch (IOException | ParseException ex) {
//            LOG.error("do post of the url:" + url + " occur error.", ex);
            throw ex;
        }
    }

    public static String doPost(String jsonString, String url) throws IOException, ParseException {
        if (jsonString == null) {
            return null;
        }
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                20000);

        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("method", "POST");

        try {
            httpPost.setEntity(new StringEntity(jsonString, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.err.println(result);
            return result;
        } catch (IOException | ParseException ex) {
//            LOG.error("do post of the url:" + url + " occur error.", ex);
            throw ex;
        }
    }

    public static String dopost3(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                20000);
        httpClient.getParams().setIntParameter(
                CoreProtocolPNames.WAIT_FOR_CONTINUE, 100);
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("method", "POST");

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            return result;
        } catch (IOException | ParseException ex) {

        }
        return null;
    }


    public static String dopost4(String url) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                20000);
        httpClient.getParams().setIntParameter(
                CoreProtocolPNames.WAIT_FOR_CONTINUE, 100);
        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("methed", "GET");

        try {
            HttpResponse response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            return result;
        } catch (IOException | ParseException ex) {

        }
        return null;
    }

    public static JSONObject getParams(HttpServletRequest request) {
        try {
            int contentLen = request.getContentLength();
            ServletInputStream inputStream = request.getInputStream();
            if (contentLen <= 0 || inputStream == null) {
                return null;
            }
            byte[] inData = new byte[contentLen];
            inputStream.read(inData);
            String inputDataStr = new String(inData, "UTF-8");
            if (inputDataStr.contains("\n")) {
                inputDataStr = inputDataStr.replaceAll("\n", "");
            }
            JSONObject inputDataJSON = JSONObject.parseObject(inputDataStr);
            return inputDataJSON;
        } catch (IOException ex) {

        }
        return null;
    }

}
