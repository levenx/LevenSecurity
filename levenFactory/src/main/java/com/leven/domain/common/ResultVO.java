package com.leven.domain.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 孙乐进 on 2019/2/25.
 */
@Data
public class ResultVO<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public ResultVO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVO(int code, String massage, T data) {
        this.code = code;
        this.message = massage;
        this.data = data;
    }

    public ResultVO code(int code) {
        this.code = code;
        return this;
    }

    public ResultVO massage(String massage) {
        this.message = massage;
        return this;
    }

    public ResultVO T(T data) {
        this.data = data;
        return this;
    }

    public static ResultVO ofMessage(int code, String message) {
        return new ResultVO(code, message);
    }

    public static <T> ResultVO ofSuccess(T data) {
        return new ResultVO(Status.SUCCESS.getCode(), Status.SUCCESS.getDescribe(), data);
    }

    public static ResultVO ofStatus(Status status) {
        return new ResultVO(status.getCode(), status.getDescribe());
    }

    public static <T> ResultVO ofStatus(Status status, T data) {
        return new ResultVO(status.getCode(), status.getDescribe(), data);
    }
}
