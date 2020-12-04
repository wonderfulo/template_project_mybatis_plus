package com.cxy.util;

import com.cxy.utils.date.DateUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;


public class FileUtils {
	public static byte[] getBytesFromFile(File file) {  
        byte[] ret = null;  
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {  
            if (file == null) {  
                return null;  
            }  
            in = new FileInputStream(file);  
            out = new ByteArrayOutputStream(4096);  
            byte[] b = new byte[4096];  
            int n;  
            while ((n = in.read(b)) != -1) {  
                out.write(b, 0, n);  
            }  
            in.close();  
            out.close();  
            ret = out.toByteArray();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
        	try{
        		if(in != null) in.close();
        		if(out != null) out.close();
        	} catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return ret;  
	}
	public static byte[] file2byte(File file){
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			int length = (int) file.length();
			byte[] bytes = new byte[length];
			int offset = 0;
            int numRead = 0;
            while(offset < bytes.length && (numRead = is.read(bytes,offset,bytes.length - offset)) >= 0){
                offset+=numRead;
            }
            return bytes;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		} finally{
			try {
				if(is != null) 
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void downFile(HttpServletResponse response,String server_path,String str) {
        try {
            String file_abs_path = server_path + str;
            File file = new File(file_abs_path);
            if (file.exists()) {
                InputStream in_s = new BufferedInputStream(new FileInputStream(file_abs_path));
                byte[] buffer = new byte[in_s.available()];
                in_s.read(buffer);
                in_s.close();
                response.reset();
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(), "ISO-8859-1"));
                response.addHeader("Content-Length", "" + file.length());
                OutputStream out_s = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                out_s.write(buffer);
                out_s.flush();
                out_s.close();

            } else {
//				response.sendRedirect("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downFileZip(HttpServletResponse response,String server_path,String str){
        try{
            String file_abs_path = server_path + str;
            File file = new File(file_abs_path);
            if(file.exists()){
                InputStream in_s = new BufferedInputStream(new FileInputStream(file_abs_path));
                byte[] buffer = new byte[in_s.available()];
                in_s.read(buffer);
                in_s.close();
                response.reset();
                response.addHeader("Content-Disposition","attachment;filename="+new String(String.valueOf((DateUtil.getNowUtilDate()).getTime()).getBytes(),"ISO-8859-1")+".zip");
                response.addHeader("Content-Length", ""+file.length());
                OutputStream out_s = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                out_s.write(buffer);
                out_s.flush();
                out_s.close();

            }else{
//				response.sendRedirect("");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
	}
	
}
