package com.leven.security.validdate.impl;

import com.leven.security.validdate.image.ImageCode;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 孙乐进 on 2019/3/1.
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    /**
     * 发送验证码
     *
     * @param request
     * @param code
     */
    @Override
    public void send(HttpServletRequest request, HttpServletResponse response, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }
}
