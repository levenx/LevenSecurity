package com.leven.web.utils;

import com.leven.security.validdate.image.ImageCode;
import com.leven.security.validdate.ValidateCodeGenerator;

/**
 * Created by 孙乐进 on 2019/2/28.
 */
//@Component("imageCodeGenerator")
public class MyImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate() {
        System.out.print("MyImageCodeGenerator");
        return null;
    }
}
