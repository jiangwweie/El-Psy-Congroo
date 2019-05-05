package com.sg.cristina.util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jw
 * Desc:  接口返回结果对象
 */
@SuppressWarnings("unused")
@Getter
@Setter
@ApiModel(description = "返回结果")
public final class ResultModel<T> {
    @ApiModelProperty("是否成功: true or false")
    private boolean success;
    @ApiModelProperty("描述性原因")
    private String message;
    @ApiModelProperty("业务数据")
    private T data;

    public ResultModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResultModel() {
    }

    public ResultModel(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static<T> ResultModel<T> success(T data) {
        return new ResultModel<>(true, "SUCCESS", data);
    }


    public static<T> ResultModel<T> success(String message, T data) {
        return new ResultModel<>(true, message, data);
    }


    public static ResultModel failure() {
        return new ResultModel<>(false, "FAILURE", null);
    }


    public static ResultModel failure(String message) {
        return new ResultModel<>(false, message, null);
    }
}