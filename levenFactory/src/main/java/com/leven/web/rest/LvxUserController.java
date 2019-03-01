package com.leven.web.rest;

import com.leven.domain.common.ResultVO;
import com.leven.security.validdate.ValidateCode;
import com.leven.security.validdate.ValidateCodeProcessor;
import com.leven.security.validdate.image.ImageCode;
import com.leven.security.validdate.ValidateCodeGenerator;
import com.leven.security.validdate.sms.SmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.leven.security.properties.LvxSecurityConstant.IMAGE_CODE_KEY;

/**
 * Created by 孙乐进 on 2019/2/25.
 */
@RestController
public class LvxUserController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/me")
    public ResultVO userDetails(){
        return ResultVO.ofSuccess(null);
    }


    /**
     * 获取验证码图片
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/{type}")
    public void cratedCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        validateCodeProcessors.get(type+"ValidateCodeProcessor").create(request,response);
    }


}
