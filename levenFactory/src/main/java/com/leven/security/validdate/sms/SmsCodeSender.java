package com.leven.security.validdate.sms;

/**
 * Created by 孙乐进 on 2019/3/1.
 */
public interface SmsCodeSender {

    public void send(String mobile, String code);
}
