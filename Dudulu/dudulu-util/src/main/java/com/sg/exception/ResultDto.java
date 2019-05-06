package com.sg.exception;

import lombok.*;

/**
 * @Author: jiangwei
 * @Date: 2019-05-05
 * @Desc:
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResultDto {
    private Integer code ;
    private String message;
    private Object data ;

    public ResultDto() {
        super();
    }

    public  ResultDto success(Object data){
        this.code =ResultEnum.SUCCESS.getCode();
        this.message =ResultEnum.SUCCESS.getMsg();
        this.data = data ;
        return this ;
    }

    public ResultDto error(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMsg();
        return this ;
    }
}
