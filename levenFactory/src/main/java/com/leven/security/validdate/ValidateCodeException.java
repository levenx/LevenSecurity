package com.leven.security.validdate;


import org.springframework.security.core.AuthenticationException;

/** 验证码异常
 * Created by 孙乐进 on 2019/2/28.
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg){
        super(msg);
    }
}
