package com.leven.security.validdate.sms;

import com.leven.security.properties.LvxSecurityProperties;
import com.leven.security.validdate.ValidateCode;
import com.leven.security.validdate.ValidateCodeGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by 孙乐进 on 2019/3/1.
 */
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private LvxSecurityProperties lvxSecurityProperties;

    @Override
    public ValidateCode generate() {
        return new ValidateCode("111", LocalDateTime.now().plusSeconds(lvxSecurityProperties.getRememberMeSeconds()));
    }
}
