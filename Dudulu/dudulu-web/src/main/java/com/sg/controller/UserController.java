package com.sg.cristina.controller;

import com.sg.cristina.entity.SgUser;
import com.sg.cristina.service.UserService;
import com.sg.cristina.util.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiangwei
 * @Date: 2019/4/20
 * @Desc:
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    UserService userService ;

    @GetMapping("select/{id}")
    public ResultModel getuser(@PathVariable Integer id){
        SgUser user = userService.selectByPrimaryKey(id);
        return this.success("success",user);
    }
}
