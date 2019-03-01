package com.leven.security.validdate.sms;

/**
 * Created by 孙乐进 on 2019/3/1.
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.print("发送验证码:"+code);
    }
}
