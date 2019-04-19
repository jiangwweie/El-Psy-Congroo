package com.sg.cristina;

import com.sg.cristina.util.ResultEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangwei
 * @Date: 2019/4/19
 * @Desc:
 */
public class Test {

    public static void main(String[] args) {
        ResultEnum success = ResultEnum.SUCCESS;

        Map map = new HashMap();
        map.put("code",success.getCode());
        map.put("msg",success.getMsg());
        System.out.println(map.toString());
    }
}
