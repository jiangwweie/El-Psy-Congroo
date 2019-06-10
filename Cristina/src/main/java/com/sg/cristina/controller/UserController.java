package com.sg.cristina.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.cristina.dao.UserDao;
import com.sg.cristina.entity.CasPrincipalUser;
import com.sg.cristina.entity.SgUser;
import com.sg.cristina.service.UserService;
import com.sg.cristina.util.ResultModel;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangwei
 * @Date: 2019/4/20
 * @Desc:
 */
@RestController
@RequestMapping("user")
@Log4j
public class UserController extends BaseController{

    @Autowired
    UserService userService ;

    @Autowired
    UserDao userDao;

    @Reference
    com.sg.UserService dubboUserservice ;

    @GetMapping("select/{id}")
    public ResultModel getuser(@PathVariable Integer id){
        SgUser user = userService.selectByPrimaryKey(id);
        return this.success("success",user);
    }

    @GetMapping("{id}")
    public Object getDubboUser(@PathVariable Integer id){
        com.sg.SgUser sgUser = dubboUserservice.selectByPrimaryKey(id);
        return sgUser.toString() ;
    }
    /**
     * 1. cas 服务端会通过post请求，并且把用户信息以"用户名:密码"进行Base64编码放在authorization请求头中
     * 2. 返回200状态码并且格式为  重点  {"@class":"org.apereo.cas.authentication.principal.SimplePrincipal","id":"casuser","attributes":{}} 是成功的
     * 2. 返回状态码403用户不可用；404账号不存在；423账户被锁定；428过期；其他登录失败
     * @param httpHeaders
     * @return
     */
    @RequestMapping("/login")
    public Object login(@RequestHeader HttpHeaders httpHeaders){

        log.info("开始验证服务");
        UserTemp userTemp =null;

        com.sg.SgUser sgUser = null ;
        try {
             userTemp = obtainUserFormHeader(httpHeaders);
            //尝试查找用户库是否存在
             sgUser = this.dubboUserservice.selectByUsername(userTemp.username);
            if (sgUser != null) {
                if (!sgUser.getPassword().equals(userTemp.password)) {
                    //密码不匹配
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
                //if (!"0".equals(user.getState())) {
                //    //用户已锁定
                //    return new ResponseEntity(HttpStatus.LOCKED);
                //}
            } else {
                //不存在 404
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("用户认证错误", e);
            new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //成功返回json
        return new CasPrincipalUser(sgUser);
    }

    /**
     * This allows the CAS server to reach to a remote REST endpoint via a POST for verification of credentials.
     * Credentials are passed via an Authorization header whose value is Basic XYZ where XYZ is a Base64 encoded version of the credentials.
     * @param httpHeaders
     * @return
     * @throws UnsupportedEncodingException
     */
    private UserTemp obtainUserFormHeader(HttpHeaders httpHeaders) throws UnsupportedEncodingException {

        //cas服务端会通过把用户信息放在请求头authorization中，并且通过Basic认证方式加密
        String authorization = httpHeaders.getFirst("authorization");
        if(StringUtils.isEmpty(authorization)){
            return null;
        }

        String baseCredentials = authorization.split(" ")[1];
        //用户名:密码
        String usernamePassword = new String(Base64Utils.decodeFromString(baseCredentials), "UTF-8");
        String[] credentials = usernamePassword.split(":");

        return new UserTemp(credentials[0], credentials[1]);
    }

    /**
     * 从请求头中获取用户名和密码
     */
    private class UserTemp {
        private String username;
        private String password;

        public UserTemp(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
