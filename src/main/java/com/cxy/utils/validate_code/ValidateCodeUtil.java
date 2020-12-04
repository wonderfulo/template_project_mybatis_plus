package com.cxy.utils.validate_code;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * 验证码生成工具类
 */
public class ValidateCodeUtil {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    public static void createImage(String code, String path, String imageName) {
        Random rand = new Random();
        BufferedImage buffImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImage.getGraphics();
        //将原来的Graphhics对象转为Graphics2D
        //（主要是为了使用Graphics2D里的rotate方法，将文字绘画时旋转，实现东倒西歪的效果）
        Graphics2D g_2D = (Graphics2D) g;
        //设置绘画背景的颜色，因背景色淡一点，所以minColor值高一点130
        g_2D.setColor(randomColor(130, 255));
        //绘画一个填充颜色的3D效果的矩形（就是普通矩形外边多了一圈东西）
        g_2D.fill3DRect(0, 0, WIDTH, HEIGHT, true);
        g_2D.setFont(new Font("宋体", Font.PLAIN, 50));
        //控制旋转的方向，（带负号是因为负数相乘是正数，这样就能实现顺时针和逆时针交替旋转）
        double rotateDir = -1;
        for (int i = 0; i < code.length(); i++) {
            //设置绘画字体颜色，要凸现文字所以颜色要深偏暗
            g_2D.setColor(randomColor(30, 120));
			/*rotate这个用的时候有点坑，比方上次旋转10度，下次旋转10度时在原先基础上再旋转10度，
			一直下去字会被旋转到图片外的*/
            g_2D.rotate(rand.nextInt(10) * Math.PI / 180 * rotateDir, 13 + i * 30 + 15, 20);
            g_2D.drawString(code.substring(i, i + 1), 13 + i * 30, WIDTH / 3);
            //重点，（要考，哈哈），解决rotate旋转问题
            rotateDir *= -1.2;
        }
        //绘制20条干扰线
        imageBackground(g_2D, 20);
        g_2D.dispose();
        try {
            File file = new File(path, imageName + ".jpg");
            ImageIO.write(buffImage, "jpeg", file);
            file.createNewFile();
        } catch (Exception e) {
        }
    }

    /**
     * 返回一个有最小范围的随机Color对象
     *
     * @param minColor 一个int类型数据，表示颜色最小底线
     * @param minColor 一个int类型数据，表示颜色最大底线
     * @return
     */
    public static Color randomColor(int minColor, int maxColor) {
        //规范输入参数
        if (minColor < 0 || minColor > maxColor || maxColor > 255) {
            throw new IllegalAccessError();
        }
        Random rand = new Random();
        return new Color(
                minColor + rand.nextInt(maxColor - minColor)
                , minColor + rand.nextInt(maxColor - minColor)
                , minColor + rand.nextInt(maxColor - minColor)
        );
    }

    /**
     * 绘画一定数量的干扰线
     *
     * @param g_2D Graphics2D对象，明确绘画目标
     * @param num  一个int类型数据，表示干扰线数
     * @return 返回操作过后的Graphics2D对象
     */
    public static Graphics2D imageBackground(Graphics2D g_2D, int num) {
        int x1, x2, y1, y2;
        Random rand = new Random();

        for (int i = 0; i < num; i++) {
            x1 = rand.nextInt(WIDTH);
            y1 = rand.nextInt(HEIGHT);
            x2 = rand.nextInt(WIDTH);
            y2 = rand.nextInt(HEIGHT);
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            g_2D.setColor(color);
            g_2D.drawLine(x1, y1, x2, y2);
        }
        return g_2D;
    }

}
