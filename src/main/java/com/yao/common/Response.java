package com.yao.common;

import lombok.Data;

/**
 * 通用返回结果类
 *
 * @date: 2023-01-29
 * @author: yao
 */
@Data
public class Response<T> {
    private Integer code;
    private T data;
    private String msg;

    public static <T> Response<T> success(T o) {
        Response<T> r = new Response<>();
        r.data = o;
        r.code = 1;
        return r;
    }

    public static <T> Response<T> error(String message) {
        Response<T> r = new Response<>();
        r.msg = message;
        r.code = 0;
        return r;
    }
}
