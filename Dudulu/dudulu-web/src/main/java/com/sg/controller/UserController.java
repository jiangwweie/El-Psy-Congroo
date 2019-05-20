package com.sg.controller;


import com.sg.SgUser;
import com.sg.exception.MyException;
import com.sg.exception.ResultDto;
import com.sg.exception.ResultEnum;
import com.sg.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/{id}")
    public Object getuser(@PathVariable Integer id){
        SgUser user = userService.selectByPrimaryKey(id);
        if (user!=null){
            return new ResultDto().success(user);
        }else {
            return new ResultDto().error(ResultEnum.NO_DATA);
        }
    }

    @RequestMapping("add")
    public Object addUser(@Valid SgUser sgUser, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuffer sb = new StringBuffer();
            for (ObjectError error:allErrors){
                sb.append(error.getDefaultMessage()+ "  ");
            }
           throw new MyException(ResultEnum.LESS_INPARAM.getCode(),sb.toString());
        }
        return new ResultDto().success("success");
    }
}
