package com.sg.controller;


import com.sg.SgUser;
import com.sg.impl.UserServiceImpl;
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
public class UserController {

    @Autowired
    UserServiceImpl userService ;

    @GetMapping("select/{id}")
    public Object getuser(@PathVariable Integer id){
        SgUser user = userService.selectByPrimaryKey(id);
        return user;
    }
}
