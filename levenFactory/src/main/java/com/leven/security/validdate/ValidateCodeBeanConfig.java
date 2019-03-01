package com.leven.security.validdate;

import com.leven.security.properties.LvxSecurityProperties;
import com.leven.security.validdate.image.ImageCodeGenerator;
import com.leven.security.validdate.sms.DefaultSmsCodeSender;
import com.leven.security.validdate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 孙乐进 on 2019/2/28.
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private LvxSecurityProperties lvxSecurityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setLvxSecurityProperties(lvxSecurityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
