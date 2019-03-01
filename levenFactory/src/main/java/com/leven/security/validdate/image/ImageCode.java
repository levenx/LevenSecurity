package com.leven.security.validdate.image;

import com.leven.security.validdate.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by 孙乐进 on 2019/2/27.
 */
@Data
public class ImageCode extends ValidateCode implements Serializable{

    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,LocalDateTime.now().plusSeconds(expireIn));
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }

}
