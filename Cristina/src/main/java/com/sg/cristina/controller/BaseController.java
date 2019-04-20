package com.sg.cristina.controller;

import com.sg.cristina.util.ResultModel;

/**
 * @Author: jiangwei
 * @Date: 2019/4/20
 * @Desc:
 */
public class BaseController {


    public ResultModel success(String message, Object object) {
        ResultModel resultModel = new ResultModel(true, message, object);
        return resultModel;
    }


    public ResultModel error(String message, Object object) {
        ResultModel resultModel = new ResultModel(false, message, object);
        return resultModel;
    }

    public ResultModel error(String message) {
        ResultModel resultModel = new ResultModel(false, message);
        return resultModel;
    }
}
