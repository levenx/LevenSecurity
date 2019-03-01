package com.leven.security.validdate.image;

import com.leven.security.properties.LvxSecurityProperties;
import com.leven.security.validdate.ValidateCodeGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by 孙乐进 on 2019/2/28.
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {


    private LvxSecurityProperties lvxSecurityProperties;

    @Override
    public ImageCode generate() {
        int width = lvxSecurityProperties.getCode().getWidth();
        int height = lvxSecurityProperties.getCode().getHeight();
        int length = lvxSecurityProperties.getCode().getLength();
        int expireIn = lvxSecurityProperties.getCode().getExpireIn();
        String randomCode = getRandomCode(length,null);
        BufferedImage codeImage = createCodeImage(width,height,randomCode);
        return new ImageCode(codeImage,randomCode,expireIn);
    }


    /**
     * 生成随机码图片
     * @param width
     * @param height
     * @param randomCode
     */
    public  BufferedImage createCodeImage(int width, int height, String randomCode){
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (randomCode.length() + 2);// 每个字符的宽度(左右各空出一个字符)
        fontHeight = height / 2;// 字体的高度
        codeY = height - 4;

        // 图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体,可以修改为其它的
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, fontHeight);
        g.setFont(font);

        for (int i = 0; i < randomCode.length(); i++) {
            // 设置随机开始和结束坐标
            int xs = random.nextInt(width);// x坐标开始
            int ys = random.nextInt(height);// y坐标开始
            int xe = xs + random.nextInt(width / 8);// x坐标结束
            int ye = ys + random.nextInt(height / 8);// y坐标结束

            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        // 随机产生codeCount个字符的验证码。
        String[] randoms = randomCode.split("");
        for (int i = 0; i < randoms.length;i++) {
            String strRand = randoms[i];
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
        }
        return buffImg;
    }


    /**
     * 生成随机验证码
     * @param length   长度
     * @param exChars  排除的字符
     * @return
     */
    public  String getRandomCode(int length,String exChars) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length){
            int t = random.nextInt(123);
            if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                sb.append((char) t);
            }
        }
        return sb.toString();
    }


    public LvxSecurityProperties getLvxSecurityProperties() {
        return lvxSecurityProperties;
    }

    public void setLvxSecurityProperties(LvxSecurityProperties lvxSecurityProperties) {
        this.lvxSecurityProperties = lvxSecurityProperties;
    }
}
