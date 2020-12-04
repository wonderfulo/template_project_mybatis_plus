package com.cxy.qr_code;

import com.cxy.utils.qr_code.QRCodeUtil;
import com.cxy.utils.validate_code.ValidateCodeUtil;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

@SpringBootTest
public class QrCode {

    /**
     * 生成二维码，并保存图片
     */
    @Test
    public void createQrCode() {
        String codeUrl = "陈翔宇666";
        String path = "D:" + File.separator + "QrCode";
        String imageName = "陈翔宇的二维码";
        try {
            //创建一个BufferedImage对象,用来操作绘画二维码
            BufferedImage image = QRCodeUtil.createImage(codeUrl, null, false);
//            ByteArrayOutputStream bs = new ByteArrayOutputStream();
//            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
//            ImageIO.write(image, "png", imOut);
//            InputStream in = new ByteArrayInputStream(bs.toByteArray());
//            String fileName = "qr" + new Random().nextInt(99999999) + ".jpg";
//            QiNiuUtil.upload(in, fileName);
//            qrcodeUrl = "http://" + QiNiuUtil.DOMAIN + "/" + fileName;

            File file = new File(path, imageName + ".png");
            ImageIO.write(image, "png", file);

            //真实环境追加以下步骤
            //文件名称+ 随机字符串
            //新增或更新文件服务器中该文件信息

            file.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 生成验证码，并保存图片
     */
    @Test
    public void validateCode() {
        String code = "456789";
        String path = "D:" + File.separator + "QrCode";
        String imageName = "陈翔宇的验";

        ValidateCodeUtil.createImage(code, path, imageName);
    }




}
