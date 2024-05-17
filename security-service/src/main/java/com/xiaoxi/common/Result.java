package com.xiaoxi.common;

import lombok.Data;

/**
 * @Author： momo
 * 
 */
@Data
public class Result<T> {
    private T data;

    private String message ;

    private Integer code ;

    public Result() {
    }

    public Result(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    public static Result success() {
        Result result = new Result(200, "请求成功");
        result.setData(null);
        return result;
    }

    public static <T> Result<T> success(T t) {
        Result result = success();
        result.setData(t);
        return result;
    }

    public static Result fail(String message) {
        Result result = new Result();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static Result fail(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
