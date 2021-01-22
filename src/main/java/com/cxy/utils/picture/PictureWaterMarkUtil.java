package com.cxy.utils.picture;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PictureWaterMarkUtil {
	// 水印透明度
    private static float alpha = 0.5f;
    // 水印文字大小
    public static final int FONT_SIZE = 28;
    // 水印文字字体
    private static Font font = new Font ("微软雅黑", Font.BOLD, FONT_SIZE);
    // 水印文字颜色
    private static Color color = Color.white;
    // 水印之间的间隔
    private static final int XMOVE = 80;
    // 水印之间的间隔
    private static final int YMOVE = 80;

    /**
     * 获取文本长度。汉字为1:1，英文和数字为2:1
     */
    private static int getTextLength (String text) {
        int length = text.length ();
        for (int i = 0; i < text.length (); i++) {
            String s = String.valueOf (text.charAt (i));
            if (s.getBytes ().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }

    /**
     * 给图片添加水印文字
     *
     * @param logoText 水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void imageByText (String logoText, String srcImgPath, String targerPath) {
        imageByText (logoText, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText
     * @param srcImgPath
     * @param targerPath
     * @param degree
     */
    public static void imageByText (String logoText, String srcImgPath, String targerPath, Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        try {
            // 源图片
            Image srcImg = ImageIO.read (new File (srcImgPath));
            int width = srcImg.getWidth (null);// 原图宽度
            int height = srcImg.getHeight (null);// 原图高度
            BufferedImage buffImg = new BufferedImage (srcImg.getWidth (null), srcImg.getHeight (null),
                    BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics ();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint (RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage (srcImg.getScaledInstance (srcImg.getWidth (null), srcImg.getHeight (null), Image.SCALE_SMOOTH),
                    0, 0, null);
            // 设置水印旋转
            if (null != degree) {
                g.rotate (Math.toRadians (degree), (double) buffImg.getWidth () / 2, (double) buffImg.getHeight () / 2);
            }
            // 设置水印文字颜色
            g.setColor (color);
            // 设置水印文字Font
            g.setFont (font);
            // 设置水印文字透明度
            g.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC_ATOP, alpha));

            int x = -width / 2;
            int y = -height / 2;
            int markWidth = FONT_SIZE * getTextLength (logoText);// 字体长度
            int markHeight = FONT_SIZE;// 字体高度

            // 循环添加水印
            while (x < width * 1.5) {
                y = -height / 2;
                while (y < height * 1.5) {
                    g.drawString (logoText, x, y);

                    y += markHeight + YMOVE;
                }
                x += markWidth + XMOVE;
            }
            // 释放资源
            g.dispose ();
            // 生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write (buffImg, "JPG", os);
            System.out.println ("添加水印文字成功!");
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace ();
            }
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
    }

    public static String safeUrlBase64Encode(byte[] data){
        String encodeBase64 = new BASE64Encoder().encode(data);
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replaceAll("=", "");
        return safeBase64Str;

    }
    
    /**
     * 添加水印处理(背景水印) 黑白旋转45度
     * @param logoText
     * @return
     */
    public String addWaterMarkOperator(String logoText){
    	String markUrl="";
    	String picUrl="http://supershoper.xxynet.com/vsvz1553070520421";//透明图片
    	String fontColor="#FFFFFF"; //字体颜色
    	String text=safeUrlBase64Encode(logoText.getBytes());
    	String color=safeUrlBase64Encode(fontColor.getBytes());
    	String water="imageView2/0/q/75|watermark/2/text/"+text+"/font/5a6L5L2T/fontsize/360/fill/"+color+"/dissolve/50/gravity/SouthEast/dx/10/dy/10|imageslim";
		String rotate="imageMogr2/rotate/45"; //旋转45度
		markUrl=picUrl+"?"+water+"|"+rotate;
    	return markUrl;
    }
    
    //图片水印
	public String addWaterMark(String logoText){
    	String text=safeUrlBase64Encode(logoText.getBytes());
    	String fontColor="#FFFFFF"; //字体颜色 白色
    	String color=safeUrlBase64Encode(fontColor.getBytes());
    	
    	//String text2=Base64.encodeBase64String("hello World".getBytes());
    	String fontColor2="#000000"; //字体颜色 #9D9D9D 黑色
    	String color2=safeUrlBase64Encode(fontColor2.getBytes());//http://supershoper.xxynet.com/vsvz1554990541635
    	/*String fontColor=Base64.encodeBase64String("#FFFFFF".getBytes()); http://supershoper.xxynet.com/vsvz1554990088123,http://supershoper.xxynet.com/vsvz1553070520421
    	String water="imageView2/0/q/75|watermark/2/text/"+text+"/font/5a6L5L2T/fontsize/360/fill/"+fontColor+"/dissolve/50/gravity/SouthEast/dx/10/dy/10|imageslim";*/
    	String water="http://supershoper.xxynet.com/vsvz1554990541635?watermark/3/text/" + text +"/font/5a6L5L2T/fontsize/360/fill/"+color2+"/dissolve/100/gravity/SouthEast/dx/1/dy/1";
    	String waterTwo="/text/" + text +"/font/5a6L5L2T/fontsize/360/fill/"+color+"/dissolve/100/gravity/SouthEast/dx/2/dy/2";
    	return water+waterTwo;
    	
    }

    public static void main(String xp[]){
       /* String srcImgPath = "C:\\Users\\chenl\\Desktop\\a.jpg";
        // 水印文字
        String logoText = "Hello World";
        String targerTextPath2 = "C:\\Users\\chenl\\Desktop\\b.jpg";
        System.out.println ("给图片添加水印文字开始...");
        // 给图片添加正水印文字
        PictureWaterMarkUtil.ImageByText (logoText, srcImgPath, targerTextPath2, null);
        // 给图片添加斜水印文字
        PictureWaterMarkUtil.ImageByText (logoText, srcImgPath, targerTextPath2, -40);
        System.out.println ("给图片添加水印文字结束...");*/
    	/*new PictureWaterMarkUtil().addWaterMark("demo csb");*/
        System.out.println(new PictureWaterMarkUtil().addWaterMarkOperator("测试测试"));
        System.out.println(new PictureWaterMarkUtil().addWaterMark("测试测试"));
    }
}
