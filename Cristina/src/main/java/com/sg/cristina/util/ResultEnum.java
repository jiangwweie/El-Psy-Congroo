package com.sg.cristina.util;

public enum ResultEnum {

    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    NO_DATA(501,"没有找到相关数据");

    int code;

    String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
