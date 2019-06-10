package com.sg.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangwei
 * @Date: 2019-05-05
 * @Desc: 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object errorHandler(HttpServletRequest request, Exception e) throws Exception {
        e.printStackTrace();
        if (e instanceof UnauthorizedException) {
            return new ResultDto().error(ResultEnum.NO_AUTH);
        } else if (e instanceof MyException) {
            MyException myException = (MyException) e;
            return ResultDto.builder().code(myException.getCode()).message(myException.getMessage()).build();
        } else {
            return new ResultDto().error(e);
        }
    }


}
