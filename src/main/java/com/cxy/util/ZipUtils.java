package com.cxy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 生成zip文件
 */
public class ZipUtils {
	public static void zipFiles(List src_file,File zip_file){
		byte data[] = new byte[1024];  
		try{
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip_file));
			for(int i = 0,len = src_file.size(); i < len; i++){
				File file = (File)src_file.get(i);
				zout.putNextEntry(new ZipEntry(file.getName()));
				FileInputStream fis = new FileInputStream(file);  
				int count;  
		        while ((count = fis.read(data)) != -1) {  
		        	zout.write(data, 0, count);  
		        }  
		        fis.close();  
				zout.closeEntry();
			}
			zout.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
