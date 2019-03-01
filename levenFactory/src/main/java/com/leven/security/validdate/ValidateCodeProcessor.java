package com.leven.security.validdate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 校验处理器，封装不同校验码的处理逻辑
 * Created by 孙乐进 on 2019/3/1.
 */
public interface ValidateCodeProcessor {

    public static String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 常见校验码
     */
    void create(HttpServletRequest request, HttpServletResponse response) throws Exception;


    /**
     * 校验验证码
     *
     * @throws Exception
     */
    void validate(HttpServletRequest request, HttpServletResponse response);
}
