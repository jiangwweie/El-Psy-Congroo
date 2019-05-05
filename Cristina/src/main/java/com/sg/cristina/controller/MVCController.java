package com.sg.cristina.controller;

import com.alibaba.fastjson.JSONObject;
import com.sg.cristina.entity.SgUser;
import com.sg.cristina.util.ResultModel;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: jiangwei
 * @Date: 2019/4/20
 * @Desc:  controller to test springmvc
 */

@RestController
@RequestMapping("test")
public class MVCController {

    /**
     * 路径参数
     * @param id
     * @return
     */
    @RequestMapping("1/{id}")
    public ResultModel test(@PathVariable int id){
        String str = " recieve param from url : "+id ;
        return new ResultModel(true,str,id);
    }


    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("2")
    public ResultModel test2(@RequestParam int id ){
        String str = " recieve param from url : "+id ;
        return new ResultModel(true,str,id);
    }

    /**
     * postman测试:
     * content-type application/json
     * 直接传键值对即可
     * @param reqMap
     * @return
     */
    @RequestMapping("3")
    public ResultModel test3(@RequestBody Map<String,Object> reqMap){
        Object param1 = reqMap.get("param1");
        Object param2 = reqMap.get("param2");
        Object json = JSONObject.toJSON(reqMap);
        return new ResultModel(true,"",json);
    }


    /**
     * postman测试传参为：
     * content-type：application/x-www-form-urlencoded 、、、、参数为listParam[]  1   listParam[]  2   listParam[]  3
     * list
     * @param param
     * @return
     */
    @RequestMapping("4")
    private ResultModel test4(@RequestParam("listParam[]") List<String> param){
        String paramStr = "Request successful. Post param : List<String> - " + param.toString();
        return  new ResultModel(true,"",paramStr);
    }


    @RequestMapping("5")
    private ResultModel test5(@Valid SgUser sgUser, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error:allErrors){
                System.out.println(error.getObjectName()+" : "+error.getDefaultMessage());
            }
        }
        System.out.println("ok");
        return  new ResultModel(true,"",sgUser);
    }
}
