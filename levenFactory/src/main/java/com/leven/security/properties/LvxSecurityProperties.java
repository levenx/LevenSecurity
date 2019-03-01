package com.leven.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author 孙乐进
 * @date 2019/2/25
 */
@ConfigurationProperties("lvx.security")
@Component
@Data
public class LvxSecurityProperties {

    private int rememberMeSeconds = 3600;

    private LoginResponseType loginType = LoginResponseType.JSON;

    private PageProperties page = new PageProperties();

    private UrlProperties url = new UrlProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();


}
