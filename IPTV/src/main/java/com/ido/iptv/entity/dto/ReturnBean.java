package com.ido.iptv.entity.dto;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author Jun
 * @date 2018-09-25 14:20
 */
@Getter
@ToString
public class ReturnBean<T> implements Serializable {

    public static final int SUCCESS = 200;

    public static final int FAILURE = 400;

    public static final int ERROR = 500;

    private int code;

    private T data;

    private String msg;

    private ReturnBean(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ReturnBean<T> success(T data, String msg) {
        return new ReturnBean<>(SUCCESS, data, msg);
    }

    public static <T> ReturnBean<T> failure(String msg) {
        return new ReturnBean<>(FAILURE, null, msg);
    }

    public static <T> ReturnBean<T> error(String msg) {
        return new ReturnBean<>(ERROR, null, msg);
    }
}
