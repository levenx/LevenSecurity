package com.leven.security.validdate.impl;

import com.leven.security.validdate.ValidateCode;
import com.leven.security.validdate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 孙乐进 on 2019/3/1.
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 发送验证码
     *
     * @param request
     * @param response
     * @param code
     */
    @Override
    public void send(HttpServletRequest request, HttpServletResponse response, ValidateCode code) throws IOException {
        smsCodeSender.send("11",code.getCode());
    }
}
