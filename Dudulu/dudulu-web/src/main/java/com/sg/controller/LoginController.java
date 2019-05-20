package com.sg.controller;

import com.sg.UserService;
import com.sg.config.MyJwtGenerator;
import com.sg.redis.RedisUtil;
import com.sg.user.mapper.SgUserMapper;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.cas.profile.CasRestProfile;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.profile.ProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @Author: jiangwei
 * @Date: 2019-05-17
 * @Desc:
 */

@RestController
@RequestMapping("")
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    SgUserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Value("${cas.serviceUrl}")
    private String serviceUrl;


    @Autowired
    CasRestFormClient casRestFormClient ;

    @Autowired
    MyJwtGenerator generator;

    @RequestMapping("/user/login")
    public Object login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<>();
        J2EContext context = new J2EContext(request, response);
        final ProfileManager<CasRestProfile> manager = new ProfileManager(context);
        final Optional<CasRestProfile> profile = manager.get(true);
        //获取ticket
        TokenCredentials tokenCredentials = casRestFormClient.requestServiceTicket( serviceUrl, profile.get(), context);
        //根据ticket获取用户信息
        final CasProfile casProfile = casRestFormClient.validateServiceTicket(serviceUrl, tokenCredentials, context);
        //生成jwt token
        String token = generator.generate(casProfile);
        // TODO: 2019-05-20 缓存token 
        response.setHeader("Authorization","Bearer "+token);
        return "success";
    }


}
