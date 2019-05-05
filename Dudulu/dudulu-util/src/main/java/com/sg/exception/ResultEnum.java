package com.sg.exception;

public enum ResultEnum {
    //**********  异常  ************
    LESS_INPARAM(600,"缺少入参"),
    //**********  失败  ************

    //**********  成功  ************
    INTERNAL_ERROR(500, "系统异常！"),
    SUCCESS(200,"成功");
    ;

    private Integer code;//结果编码
    private String msg;//结果信息

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}