package com.cxy.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageHelper {
	
	 // 过滤Img标签  
	private static  String  imgwidthString="width=\"\\w*\"";
	private static  String  imgheightString="height=\"\\w*\"";
    private static String regEx_img = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";  
    private static Pattern p_image = Pattern.compile(regEx_img,  
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); 
    private static Pattern p_image_width = Pattern.compile(imgwidthString,  
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); 
    private static Pattern p_image_height = Pattern.compile(imgheightString,  
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); 
    /** 
     * 2、过滤图片 
     *  
     * @param xmlString 
     *             
     * @return 
     */  
    public static String getImgStr(String htmlStr,String w,String  h){  
        String img="",thishtmlstrString="" ,thisimg="" ;
          Matcher  m_image = p_image.matcher(htmlStr);  
         while(m_image.find()){
             img=img+","+m_image.group();
          } 
         if(img!=""){
        	 String[]  list=img.substring(1).split(",");
             //System.out.println("长度=="+img.substring(1));
             for (int i = 0; i < list.length; i++) {
             	thishtmlstrString=list[i];
                m_image=p_image_width.matcher(thishtmlstrString);
                while(m_image.find()){  
             	   thishtmlstrString =thishtmlstrString.replace(m_image.group(),"");  
               }  
                m_image=p_image_height.matcher(thishtmlstrString);
                while(m_image.find()){  
             	   thishtmlstrString = thishtmlstrString.replace(m_image.group(),"");  
               } 
              thisimg=thishtmlstrString.replace("/>", "onload='fixImage(this,"+w+","+h+")'  />");
              htmlStr = htmlStr.replace(list[i], thisimg);
    	   }
         }
        
         return htmlStr;  
    } 
   
    /** 
     * 2、过滤图片 
     *  
     * @param xmlString 
     *             
     * @return 
     */  
    public static String getImgStsr(String htmlStr,String w,String  h){  
        String img="",thishtmlstrString="" ,thisimg="" ;
          Matcher  m_image = p_image.matcher(htmlStr);  
         while(m_image.find()){
             img=img+","+m_image.group();
          } 
         if(img!=""){
        	 String[]  list=img.substring(1).split(",");
             //System.out.println("长度=="+img.substring(1));
             for (int i = 0; i < list.length; i++) {
             	thishtmlstrString=list[i];
                m_image=p_image_width.matcher(thishtmlstrString);
                while(m_image.find()){  
             	   thishtmlstrString =thishtmlstrString.replace(m_image.group(),"");  
               }  
                m_image=p_image_height.matcher(thishtmlstrString);
                while(m_image.find()){  
             	   thishtmlstrString = thishtmlstrString.replace(m_image.group(),"");  
               } 
              thisimg=thishtmlstrString.replace("/>", "   width='"+w+"'  height='"+h+"' />");
              htmlStr = htmlStr.replace(list[i], thisimg);
    	   }
         }
        
         return htmlStr;  
    } 
   
	
	/**
	 * 图片裁剪
	 * @param src
	 * @param dest
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param imgType 图片类别 ：jpg,png
	 * @throws IOException
	 */
	public static void cutImage(String src,String dest,int x,int y,int w,int h, String imgType) throws IOException{
		Iterator iterator = ImageIO.getImageReadersByFormatName(imgType);
		ImageReader reader = (ImageReader)iterator.next();
		InputStream in=new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x, y, w,h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0,param);
		ImageIO.write(bi, imgType, new File(dest));
	}
	
	public static String getImageType(String src) throws Exception{
		if(src.lastIndexOf(".")<0){
			 throw new Exception("src is not an path of image file");
	    }
		return src.substring(src.lastIndexOf(".")+1);
	}
	public static void zoomImage(String src,String dest,int w,int h) throws Exception {
		double wr=0,hr=0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		BufferedImage bufImg = ImageIO.read(srcFile);
		Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);
		wr=w*1.0/bufImg.getWidth();
		hr=h*1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		try {
			ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static String[] getStr(String str){
		String[] strs=new String[2];
		String a=str.replace("[", "#1234#").replace("]", "#1234#");
		String[] b=a.split("#1234#");
		String img="";
		String text="";
		for (int i = 0; i < b.length; i++) {
			if(!b[i].equals("")){
				int c=b[i].indexOf("img=");
				if(c!=-1){
					if(img.length()>0){
						img+=","+b[i].substring(4);
					}else{
						img+=b[i].substring(4);
					}
				}else{
					text+=b[i];
				}	
			}
		}
		strs[0]=img;
		strs[1]=text;
		return strs;
		
	}
	public static String getStr1(String str){
		String a=str.replace("[img=", "<img src='").replace("]", "' />");
		
		return a;
		
	}
	
	public static String genMinImageUrl(String img_url, String min_img_type) {
		if (img_url.indexOf("?") == -1) {
			img_url += "?" + min_img_type;
		} else {
			img_url += "&" + min_img_type;
		}
		return img_url;
	}
	public static void main(String[] args) throws IOException{
			//ImageHelper.cutImage("G:\\tmp\\图片特效\\20121224103618164\\images\\2.jpg", "G:\\tmp\\图片特效\\20121224103618164\\images\\2cut.jpg", 100, 100, 100, 200, "jpg");
	//File file = new File("G:\\tmp\\图片特效\\20121224103618164\\images\\3.2.jpg");
		
		String string="sdfdsaf[img=http://7fvhtu.com1.z0.glb.clouddn.com/ysxx1404809146073]dsfadsafdsa[img=http://7fvhtu.com1.z0.glb.clouddn.com/ysxx1404809146073]ddasfsadfas[img=http://7fvhtu.com1.z0.glb.clouddn.com/ysxx1404809146073]";
	    String thisstr=getStr1(string);
	/*	System.out.println(file.getName());
    	String fileName = file.getName();
    	System.out.println(fileName.lastIndexOf("."));
    	String fileExt = fileName.substring(fileName.lastIndexOf("."), fileName.length());*/
	
    	System.out.println(thisstr);

	}
}
