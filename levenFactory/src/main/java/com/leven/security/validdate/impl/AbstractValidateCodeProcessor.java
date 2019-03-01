package com.leven.security.validdate.impl;

import com.leven.security.properties.ValidateCodeType;
import com.leven.security.validdate.ValidateCode;
import com.leven.security.validdate.ValidateCodeException;
import com.leven.security.validdate.ValidateCodeGenerator;
import com.leven.security.validdate.ValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 孙乐进 on 2019/3/1.
 */
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {


    /**
     * 收集系统中所有的ValidateCodeGenerator的实现
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGenerators;


    /**
     * 常见校验码
     */
    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        T code = generate(request);
        save(request,code);
        send(request,response,code);
    }



    /**
     * 生成验证码
     * @return
     */
    private T generate(HttpServletRequest request){
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type+"CodeGenerator");
        return (T)validateCodeGenerator.generate();
    }

    /**
     * 获取验证码类型
     * @param request
     * @return
     */
    private String getProcessorType(HttpServletRequest request){
        return StringUtils.substringAfter(request.getRequestURI(),"/code/");
    }

    /**
     * 保存验证码
     */
    private void save(HttpServletRequest request, T validateCode){
        request.getSession().setAttribute(SESSION_KEY_PREFIX+getProcessorType(request).toUpperCase(),validateCode);
    }


    /**
     *发送验证码
     * @param request
     * @param code
     */
    public abstract void send(HttpServletRequest request,HttpServletResponse response, T code) throws IOException;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 校验验证码
     *
     * @param request
     * @throws Exception
     */
    @Override
    public void validate(HttpServletRequest request, HttpServletResponse response) {
        ValidateCodeType processorType = getValidateCodeType();

        T codeInSession = (T) request.getSession().getAttribute(SESSION_KEY_PREFIX+processorType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request,
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (!codeInSession.isExpired()) {
            request.getSession().removeAttribute(SESSION_KEY_PREFIX+getProcessorType(request).toUpperCase());
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        request.getSession().removeAttribute(SESSION_KEY_PREFIX+getProcessorType(request).toUpperCase());
    }
}
