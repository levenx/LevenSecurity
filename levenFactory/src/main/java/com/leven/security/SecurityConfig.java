package com.leven.security;

import com.leven.security.authentication.config.AbstractChannelSecurityConfig;
import com.leven.security.authentication.config.ValidateCodeSecurityConfig;
import com.leven.security.authentication.config.SmsCodeAuthenticationSecurityConfig;
import com.leven.security.properties.LvxSecurityConstant;
import com.leven.security.properties.LvxSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


/**
 *
 * @author 孙乐进
 * @date 2019/2/25
 */
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**参数配置*/
    @Autowired
    private LvxSecurityProperties lvxSecurityProperties;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private AbstractChannelSecurityConfig abstractChannelSecurityConfig;

    /**
     * 配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        abstractChannelSecurityConfig.applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)
                .and()
             .apply(smsCodeAuthenticationSecurityConfig)
                .and()
             .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(lvxSecurityProperties.getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                .and()
             .authorizeRequests()
                .antMatchers(lvxSecurityProperties.getPage().getIndexPage(),
                        lvxSecurityProperties.getPage().getIndexPath(),
                        lvxSecurityProperties.getPage().getLoginPage(),
                        lvxSecurityProperties.getPage().getLoginPath(),
                        lvxSecurityProperties.getUrl().getLoginFormUrl(),
                        LvxSecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //建表
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
