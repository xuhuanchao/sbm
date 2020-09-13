package com.xhc.sbm.bean;

import lombok.Data;

/**
 * @Author: xhc
 * @Date: 2020/3/27 10:39
 * @Description:
 */
@Data
public class ResponseResult<T> {

    private int code;

    private String message;

    private T data;


    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult success(){
        return new ResponseResult(1, "成功", null);
    }

    public static ResponseResult success(Object data){
        return new ResponseResult(1, "成功", data);
    }

    public static ResponseResult error(){
        return new ResponseResult(0, "失败", null);
    }

    public static ResponseResult error(String message){
        return new ResponseResult(0, message, null);
    }

    public static ResponseResult error(int code, String message){
        return new ResponseResult(code, message, null);
    }
}
