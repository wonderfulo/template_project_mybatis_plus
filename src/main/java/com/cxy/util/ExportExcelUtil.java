//package com.cxy.util;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//public class ExportExcelUtil {
//
//	/**
//	 * 通用导出方法
//	 * @param <T>
//	 * @param list 数据源
//	 * @param hearders 表头数组
//	 * @param title 标题
//	 * @param resp
//	 * @throws IOException
//	 */
//	public<T> void exportCommon(List<T> list, String[] hearders, String title,
//			HttpServletResponse resp) throws IOException{
//		ExportExcel<T> ex = new ExportExcel<T>();
//		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
// 	    String filename = timeFormat.format(new Date())+".xls";
// 	    resp.setContentType("application/ms-excel;charset=UTF-8");
// 	    resp.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
// 	    OutputStream out = resp.getOutputStream();
// 	    ex.exportExcelHttp(title, hearders, list, out,"yyyy-MM-dd HH:mm:ss");
// 	    out.close();
//	}
//
//	/**
//	 * 通用导出方法
//	 * @param <T>
//	 * @param list 数据源
//	 * @param hearders 表头数组
//	 * @param title 标题
//	 * @param filedNames 字段过滤数组
//	 * @param resp
//	 * @throws IOException
//	 */
//	public<T> void exportCommon(List<T> list, String[] hearders, String title,
//			String[] filedNames, HttpServletResponse resp) throws IOException{
//		ExportExcel<T> ex = new ExportExcel<T>();
//		resp.reset();
//		OutputStream out =null;
//		try{
//		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
// 	    String filename = timeFormat.format(new Date())+".xls";
//    	resp.setContentType("application/ms-excel;charset=UTF-8");
//    	resp.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
// 	    out= resp.getOutputStream();
// 	    ex.exportExcel(title, hearders, list, out, "yyyy-MM-dd HH:mm:ss", filedNames);
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//	 	    if(out!=null){
//	 	    	out.flush();
//		 	    out.close();
//	 	    }
//		}
//	}
//
//	public<T> void exportCommonWithName(List<T> list, String[] hearders, String title,
//			String[] filedNames,String fileName, HttpServletResponse resp) throws IOException{
//		ExportExcel<T> ex = new ExportExcel<T>();
//		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
// 	    String filename = fileName + timeFormat.format(new Date())+".xls";
//    	resp.setContentType("application/ms-excel;charset=UTF-8");
//    	resp.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
// 	    OutputStream out = resp.getOutputStream();
// 	    ex.exportExcel(title, hearders, list, out, "yyyy-MM-dd HH:mm:ss", filedNames);
// 	    out.close();
//	}
//}
