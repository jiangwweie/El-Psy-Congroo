package com.sg.cristina.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.cristina.constants.Constants;
import com.sg.cristina.dao.SgUserMapper;
import com.sg.cristina.entity.SgUser;
import com.sg.cristina.util.HttpUtils;
import com.sg.cristina.util.ResultModel;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jiangwei
 * @Date: 2019/4/19
 * @Desc:
 */

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    SgUserMapper userMapper;

    @RequestMapping("callback")
    public ResultModel callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //region获取code，然后用code取得access_token
        String responseCode = request.getParameter("code");
        System.out.println("code:" + responseCode);
        NameValuePair code = new BasicNameValuePair("code", responseCode);
        NameValuePair id = new BasicNameValuePair("client_id", Constants.GITHUB_CLIENT_ID);
        NameValuePair secret = new BasicNameValuePair("client_secret", Constants.GITHUB_CLIENT_SECRET);
        List<NameValuePair> list = new ArrayList<>();
        list.add(code);
        list.add(id);
        list.add(secret);
        String result = HttpUtils.doPost1(Constants.GITHUB_ACCESSTOKEN_URL, list);
        System.out.println("result：" + result);
        String access_token = (String) JSONObject.parseObject(result).get("access_token");
        System.out.println("access_token:" + access_token);
        //endregion

        //region通过access_token取得用户信息
        String user_info = HttpUtils.doGet(Constants.GITHUB_GET_USERINFO + "?access_token=" + access_token);
        JSONObject jsonObject = JSONObject.parseObject(user_info);
        String gitId = jsonObject.getString("id");
        //endregion
        System.out.println("github Id :"+gitId);
        //region保存用户信息，执行业务逻辑
        SgUser user = new SgUser();
        try {
            user = userMapper.selectByGitId(gitId);
            if (user!=null){
                System.out.println("gitUser  has  already sign up ");
                return new ResultModel(true, "success", user);
            }else {
                System.out.println("redirect to reg page... ");
                // TODO: 2019/4/20
                return new ResultModel(false,"need reg",jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //endregion
        return new ResultModel(false,"unknown error");
    }

}
