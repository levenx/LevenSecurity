package com.leven.security.validdate;

import com.leven.security.authentication.LvxAuthenticationFailHandler;
import com.leven.security.properties.LvxSecurityConstant;
import com.leven.security.properties.LvxSecurityProperties;
import com.leven.security.properties.ValidateCodeType;
import com.leven.security.validdate.image.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.leven.security.properties.LvxSecurityConstant.IMAGE_CODE_KEY;

/** 验证码过滤器
 * Created by 孙乐进 on 2019/2/28.
 */
@Component("valiateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 校验失败处理器
     */
    private LvxAuthenticationFailHandler lvxAuthenticationFailHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private LvxSecurityProperties lvxSecurityProperties;


    /**
     * 系统验证码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    /**
     * 存放需要校验的url
     */
    private Map<String,ValidateCodeType> urlMap = new HashMap<>();



    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(lvxSecurityProperties.getUrl().getLoginFormUrl(),ValidateCodeType.IMAGE);
        urlMap.put(lvxSecurityProperties.getUrl().getLoginMobileUrl(),ValidateCodeType.SMS);
        addUrlToMap(lvxSecurityProperties.getCode().getUrl(),ValidateCodeType.IMAGE);
    }


    /**
     * 将系统中需要校验验证码的url放入map中
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type){
        if(StringUtils.isNotBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString,",");
            for (String url : urls) {
                urlMap.put(url,type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ValidateCodeType type = getValidateCodeType(request);

        if(type != null){
            validateCodeProcessorHolder.findValidateCodeProcessor(type)
                    .validate(request,response);
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }


    public void setLvxAuthenticationFailHandler(LvxAuthenticationFailHandler lvxAuthenticationFailHandler) {
        this.lvxAuthenticationFailHandler = lvxAuthenticationFailHandler;
    }

    public LvxAuthenticationFailHandler getLvxAuthenticationFailHandler() {
        return lvxAuthenticationFailHandler;
    }

    public LvxSecurityProperties getLvxSecurityProperties() {
        return lvxSecurityProperties;
    }

    public void setLvxSecurityProperties(LvxSecurityProperties lvxSecurityProperties) {
        this.lvxSecurityProperties = lvxSecurityProperties;
    }
}
