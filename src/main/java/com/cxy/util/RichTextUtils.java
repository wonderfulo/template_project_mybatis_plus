package com.cxy.util;

import java.util.regex.Pattern;

public class RichTextUtils {

	public static void main(String xp[]){
		String richText = "<div><p style='color:red;'>P Text</p></div><br /><div></div><img width='199' src='_image/13/label'/>"
				        + "<img width='199' src='_image/14/label'/><span> span Text</span><em> em Text</em>"
				        + "<i> I Text </i><a>a Text</a><br> br Text</br> <iframe>iframe Text</iframe> <hr>hr Text</hr> <link>link Text</link>"
		                + "<u>U Text</u> <ul>Ul Text</ul> <li>Ul Text</li> <title>Title Text<title><label> label Text</label>";
		System.out.print(removeHtmlTag(richText));
	}
	
	/** 
     * 去除富文本编辑器标签 
     *  
     * @param inputString 
     * @return 
     */  
    public static String removeHtmlTag(String inputString) {    
        if (inputString == null)    
            return null;    
        String htmlStr = inputString; // 含html标签的字符串    
        String textStr = "";    
        Pattern p_script;
        java.util.regex.Matcher m_script;    
        Pattern p_style;
        java.util.regex.Matcher m_style;    
        Pattern p_html;
        java.util.regex.Matcher m_html;    
        try {    
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>    
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";     
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>    
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";     
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式    
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);    
            m_script = p_script.matcher(htmlStr);    
            htmlStr = m_script.replaceAll(""); // 过滤script标签    
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);    
            m_style = p_style.matcher(htmlStr);    
            htmlStr = m_style.replaceAll(""); // 过滤style标签    
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);    
            m_html = p_html.matcher(htmlStr);    
            htmlStr = m_html.replaceAll(""); // 过滤html标签    
            textStr = htmlStr;    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return textStr;// 返回文本字符串    
    }  
}
