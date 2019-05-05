package com.sg.exception;

/**
 * @Author: jiangwei
 * @Date: 2019-05-05
 * @Desc:
 */
public class MyException extends RuntimeException {

    /**
     * 注意要继承自RuntimeException，底层RuntimeException继承了Exception,
     * spring框架只对抛出的异常是RuntimeException才会进行事务回滚，
     * 如果是抛出的是Exception，是不会进行事物回滚的
     */
    private Integer code;

    public MyException(Integer code,String message) {
        //父类的构造方法本身会传message进去
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
