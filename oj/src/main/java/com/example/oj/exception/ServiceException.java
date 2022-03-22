package com.example.oj.exception;

import lombok.Getter;

/**
 * 自定义异常
 *
 * @author sjy
 * @date 2022/2/14
 **/

@Getter
public class ServiceException extends RuntimeException{

    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
