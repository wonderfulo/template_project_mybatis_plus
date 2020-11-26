//package com.cxy.util;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
//import org.apache.poi.hssf.usermodel.HSSFComment;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFHyperlink;
//import org.apache.poi.hssf.usermodel.HSSFPatriarch;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//
//
///**
// *
// *
// * @author leno
// * @version v1.0
// * @param <T>
// *            应用泛型，代表任意一个符合javabean风格的类
// *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
// *            byte[]表jpg格式的图片数据
// */
//public class ExportExcel<T>
//{
//	public void exportExcel(Collection<T> dataset, OutputStream out)
//	{
//		exportExcel("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
//	}
//
//
//	public void exportExcel(String  headTitile,String[] headers, Collection<T> dataset,
//			OutputStream out)
//	{
//		exportExcel(headTitile, headers, dataset, out, "yyyy-MM-dd HH:mm:ss");
//	}
//
//	public void exportExcel(String[] headers, Collection<T> dataset,
//			OutputStream out, String pattern)
//	{
//		exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);
//	}
//
//	/**
//	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
//	 *
//	 * @param title
//	 *            表格标题名
//	 * @param headers
//	 *            表格属性列名数组
//	 * @param dataset
//	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
//	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
//	 * @param out
//	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
//	 * @param pattern
//	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
//	 */
//	@SuppressWarnings("unchecked")
//	public void exportExcel(String title, String[] headers,
//			Collection<T> dataset, OutputStream out, String pattern)
//	{
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		// 生成一个表格
//		HSSFSheet sheet = workbook.createSheet(title);
//		// 设置表格默认列宽度为15个字节
//		sheet.setDefaultColumnWidth((short) 15);
//		// 生成一个样式
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 生成一个字体
//		HSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.VIOLET.index);
//		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 把字体应用到当前的样式
//		style.setFont(font);
//		// 生成并设置另一个样式
//		HSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		// 生成另一个字体
//		HSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//		// 把字体应用到当前的样式
//		style2.setFont(font2);
//
//		// 声明一个画图的顶级管理器
//		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//		// 定义注释的大小和位置,详见文档
//		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//				0, 0, 0, (short) 4, 2, (short) 6, 5));
//		// 设置注释内容
//		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//      //	comment.setAuthor("leno");
//
//		// 产生表格标题行
//		HSSFRow row = sheet.createRow(0);
//
//		for (short i = 0; i < headers.length; i++)
//		{
//			HSSFCell cell = row.createCell(i);
//			cell.setCellStyle(style);
//			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//
//		// 遍历集合数据，产生数据行
//		Iterator<T> it = dataset.iterator();
//		int index = 0;
//		while (it.hasNext())
//		{
//			index++;
//			row = sheet.createRow(index);
//			T t = (T) it.next();
//			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//			Field[] fields = t.getClass().getDeclaredFields();
//			for (short i = 0; i < fields.length; i++)
//			{
//				HSSFCell cell = row.createCell(i);
//				cell.setCellStyle(style2);
//				Field field = fields[i];
//				String fieldName = field.getName();
//				String getMethodName = "get"
//						+ fieldName.substring(0, 1).toUpperCase()
//						+ fieldName.substring(1);
//				try
//				{
//					Class tCls = t.getClass();
//					Method getMethod = tCls.getMethod(getMethodName,
//							new Class[]
//							{});
//					Object value = getMethod.invoke(t, new Object[]
//					{});
//					// 判断值的类型后进行强制类型转换
//					String textValue = null;
//
//					if (value instanceof Boolean)
//					{
//						boolean bValue = (Boolean) value;
//						textValue = "男";
//						if (!bValue)
//						{
//							textValue = "女";
//						}
//					}
//					else if (value instanceof Date)
//					{
//						Date date = (Date) value;
//						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//						textValue = sdf.format(date);
//					}
//					else if (value instanceof byte[])
//					{
//						// 有图片时，设置行高为60px;
//						row.setHeightInPoints(60);
//						// 设置图片所在列宽度为80px,注意这里单位的一个换算
//						sheet.setColumnWidth(i, (short) (35.7 * 80));
//						// sheet.autoSizeColumn(i);
//						byte[] bsValue = (byte[]) value;
//						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
//								1023, 255, (short) 6, index, (short) 6, index);
//						anchor.setAnchorType(2);
//						patriarch.createPicture(anchor, workbook.addPicture(
//								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
//					}
//					else
//					{
//						// 其它数据类型都当作字符串简单处理
//						textValue = value!=null?value.toString():"";
//					}
//					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//					if (textValue != null)
//					{
//						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
//						Matcher matcher = p.matcher(textValue);
//						if (matcher.matches())
//						{
//							// 是数字当作double处理
//							cell.setCellValue(Double.parseDouble(textValue));
//						}
//						else
//						{
//							HSSFRichTextString richString = new HSSFRichTextString(
//									textValue);
//						/*	HSSFFont font3 = workbook.createFont();
//							font3.setColor(HSSFColor.BLUE.index);
//							richString.applyFont(font3);*/
//							cell.setCellValue(richString);
//						}
//					}
//				}
//				catch (SecurityException e)
//				{
//					e.printStackTrace();
//				}
//				catch (NoSuchMethodException e)
//				{
//					e.printStackTrace();
//				}
//				catch (IllegalArgumentException e)
//				{
//					e.printStackTrace();
//				}
//				catch (IllegalAccessException e)
//				{
//					e.printStackTrace();
//				}
//				catch (InvocationTargetException e)
//				{
//					e.printStackTrace();
//				}
//				finally
//				{
//					// 清理资源
//
//				}
//			}
//		}
//		try
//		{
//			workbook.write(out);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public void exportExcelList(String title, String[] headers,
//			List<T> dataset, OutputStream out, String pattern)
//	{
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//
//		// 生成一个样式
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 生成一个字体
//		HSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.VIOLET.index);
//		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 把字体应用到当前的样式
//		style.setFont(font);
//		// 生成并设置另一个样式
//		HSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		// 生成另一个字体
//		HSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//		// 把字体应用到当前的样式
//		style2.setFont(font2);
//
//		int tabRow = 50000;
//		int len = dataset.size();
//		int count = len/tabRow+1;
//		for(int j=0; j<count; j++) {
//
//			// 生成一个表格
//			HSSFSheet sheet = workbook.createSheet(title+j);
//			// 设置表格默认列宽度为15个字节
//			sheet.setDefaultColumnWidth((short) 15);
//			// 声明一个画图的顶级管理器
//			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//			// 定义注释的大小和位置,详见文档
//			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//					0, 0, 0, (short) 4, 2, (short) 6, 5));
//			// 设置注释内容
//			comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//	      //	comment.setAuthor("leno");
//
//			// 产生表格标题行
//			HSSFRow row = sheet.createRow(0);
//
//			for (short i = 0; i < headers.length; i++)
//			{
//				HSSFCell cell = row.createCell(i);
//				cell.setCellStyle(style);
//				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//				cell.setCellValue(text);
//			}
//
//			int beginIdx = j*tabRow;
//			int endIdx = (j+1)*tabRow;
//			if(endIdx > len) {
//				endIdx = len;
//			}
//			// 遍历集合数据，产生数据行
//			Iterator<T> it = dataset.subList(beginIdx, endIdx).iterator();
//			int index = 0;
//			while (it.hasNext())
//			{
//				index++;
//				row = sheet.createRow(index);
//				T t = (T) it.next();
//				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//				Field[] fields = t.getClass().getDeclaredFields();
//				for (short i = 0; i < fields.length; i++)
//				{
//					HSSFCell cell = row.createCell(i);
//					cell.setCellStyle(style2);
//					Field field = fields[i];
//					String fieldName = field.getName();
//					String getMethodName = "get"
//							+ fieldName.substring(0, 1).toUpperCase()
//							+ fieldName.substring(1);
//					try
//					{
//						Class tCls = t.getClass();
//						Method getMethod = tCls.getMethod(getMethodName,
//								new Class[]
//								{});
//						Object value = getMethod.invoke(t, new Object[]
//						{});
//						// 判断值的类型后进行强制类型转换
//						String textValue = null;
//
//						if (value instanceof Boolean)
//						{
//							boolean bValue = (Boolean) value;
//							textValue = "男";
//							if (!bValue)
//							{
//								textValue = "女";
//							}
//						}
//						else if (value instanceof Date)
//						{
//							Date date = (Date) value;
//							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//							textValue = sdf.format(date);
//						}
//						else if (value instanceof byte[])
//						{
//							// 有图片时，设置行高为60px;
//							row.setHeightInPoints(60);
//							// 设置图片所在列宽度为80px,注意这里单位的一个换算
//							sheet.setColumnWidth(i, (short) (35.7 * 80));
//							// sheet.autoSizeColumn(i);
//							byte[] bsValue = (byte[]) value;
//							HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
//									1023, 255, (short) 6, index, (short) 6, index);
//							anchor.setAnchorType(2);
//							patriarch.createPicture(anchor, workbook.addPicture(
//									bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
//						}
//						else
//						{
//							// 其它数据类型都当作字符串简单处理
//							textValue = value!=null?value.toString():"";
//						}
//						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//						if (textValue != null)
//						{
//							Pattern p = Pattern.compile("^//d+(//.//d+)?$");
//							Matcher matcher = p.matcher(textValue);
//							if (matcher.matches())
//							{
//								// 是数字当作double处理
//								cell.setCellValue(Double.parseDouble(textValue));
//							}
//							else
//							{
//								HSSFRichTextString richString = new HSSFRichTextString(
//										textValue);
//							/*	HSSFFont font3 = workbook.createFont();
//								font3.setColor(HSSFColor.BLUE.index);
//								richString.applyFont(font3);*/
//								cell.setCellValue(richString);
//							}
//						}
//					}
//					catch (SecurityException e)
//					{
//						e.printStackTrace();
//					}
//					catch (NoSuchMethodException e)
//					{
//						e.printStackTrace();
//					}
//					catch (IllegalArgumentException e)
//					{
//						e.printStackTrace();
//					}
//					catch (IllegalAccessException e)
//					{
//						e.printStackTrace();
//					}
//					catch (InvocationTargetException e)
//					{
//						e.printStackTrace();
//					}
//					finally
//					{
//						// 清理资源
//
//					}
//				}
//			}
//		}
//		try
//		{
//			workbook.write(out);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//
//	/**
//	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
//	 *
//	 * @param title
//	 *            表格标题名
//	 * @param headers
//	 *            表格属性列名数组
//	 * @param dataset
//	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
//	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
//	 * @param out
//	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
//	 * @param pattern
//	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
//	 */
//	@SuppressWarnings("unchecked")
//	public void exportExcelHttp(String title, String[] headers,
//			Collection<T> dataset, OutputStream out, String pattern)
//	{
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		// 生成一个表格
//		HSSFSheet sheet = workbook.createSheet(title);
//		// 设置表格默认列宽度为15个字节
//		sheet.setDefaultColumnWidth((short) 15);
//		// 生成一个样式
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 生成一个字体
//		HSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.VIOLET.index);
//		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 把字体应用到当前的样式
//		style.setFont(font);
//		// 生成并设置另一个样式
//		HSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		// 生成另一个字体
//		HSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//		// 把字体应用到当前的样式
//		style2.setFont(font2);
//
//		// 声明一个画图的顶级管理器
//		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//		// 定义注释的大小和位置,详见文档
//		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//				0, 0, 0, (short) 4, 2, (short) 6, 5));
//		// 设置注释内容
//		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//      //	comment.setAuthor("leno");
//
//		// 产生表格标题行
//		HSSFRow row = sheet.createRow(0);
//
//		HSSFCellStyle hlink_style = workbook.createCellStyle();
//		hlink_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		hlink_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		hlink_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		hlink_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		hlink_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		hlink_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		hlink_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//		HSSFFont hlink_font = workbook.createFont();
//	    hlink_font.setUnderline(HSSFFont.U_SINGLE);
//	    hlink_font.setColor(HSSFColor.BLUE.index);
//	    hlink_style.setFont(hlink_font);
//
//		for (short i = 0; i < headers.length; i++)
//		{
//			HSSFCell cell = row.createCell(i);
//			cell.setCellStyle(style);
//			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//
//		// 遍历集合数据，产生数据行
//		Iterator<T> it = dataset.iterator();
//		int index = 0;
//		while (it.hasNext())
//		{
//			index++;
//			row = sheet.createRow(index);
//			T t = (T) it.next();
//			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//			Field[] fields = t.getClass().getDeclaredFields();
//			for (short i = 0; i < fields.length; i++)
//			{
//				HSSFCell cell = row.createCell(i);
//				cell.setCellStyle(style2);
//				Field field = fields[i];
//				String fieldName = field.getName();
//				String getMethodName = "get"
//						+ fieldName.substring(0, 1).toUpperCase()
//						+ fieldName.substring(1);
//				try
//				{
//					Class tCls = t.getClass();
//					Method getMethod = tCls.getMethod(getMethodName,
//							new Class[]
//							{});
//					Object value = getMethod.invoke(t, new Object[]
//					{});
//					// 判断值的类型后进行强制类型转换
//					String textValue = null;
//
//					if (value instanceof Boolean)
//					{
//						boolean bValue = (Boolean) value;
//						textValue = "男";
//						if (!bValue)
//						{
//							textValue = "女";
//						}
//					}
//					else if (value instanceof Date)
//					{
//						Date date = (Date) value;
//						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//						textValue = sdf.format(date);
//					}
//					else if (value instanceof byte[])
//					{
//						// 有图片时，设置行高为60px;
//						row.setHeightInPoints(60);
//						// 设置图片所在列宽度为80px,注意这里单位的一个换算
//						sheet.setColumnWidth(i, (short) (35.7 * 80));
//						// sheet.autoSizeColumn(i);
//						byte[] bsValue = (byte[]) value;
//						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
//								1023, 255, (short) 6, index, (short) 6, index);
//						anchor.setAnchorType(2);
//						patriarch.createPicture(anchor, workbook.addPicture(
//								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
//					}
//					else
//					{
//						// 其它数据类型都当作字符串简单处理
//						textValue = value!=null?value.toString():"";
//					}
//					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//					if (textValue != null)
//					{
//						if(fieldName.indexOf("HTTP")>-1 )
//						{
//							String[] Strs = textValue.split(",");
//							/*
//							 * 把for循环里面定义样式放到 循环外面去
//							 * zhjtao  20160310
//							 */
////							HSSFCellStyle hlink_style = workbook.createCellStyle();
////							hlink_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
////							hlink_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
////							hlink_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
////							hlink_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
////							hlink_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
////							hlink_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
////							hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
////							hlink_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
////
////							HSSFFont hlink_font = workbook.createFont();
////						    hlink_font.setUnderline(HSSFFont.U_SINGLE);
////						    hlink_font.setColor(HSSFColor.BLUE.index);
////						    hlink_style.setFont(hlink_font);
//							for(short j=0; j<9; j++)
//							{
//								HSSFCell cellChild = row.createCell(i+j);
//								if(Strs.length>j && textValue.replace(" ", "").length()>0)
//								{
//									// sheet.ungroupColumn(i, i+j);
//									if(Strs[j].indexOf(".mp4")>0){
//										cellChild.setCellValue("视频"+(j+1));
//									}else{
//										cellChild.setCellValue("图片"+(j+1));
//									}
//								    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
//
//								    link.setAddress(Strs[j]);
//
//								    cellChild.setHyperlink(link);
//								    cellChild.setCellStyle(hlink_style);
//								}/*else
//									cellChild.setCellStyle(style2);*/
//							}
//
//						}else
//						{
//							Pattern p = Pattern.compile("^//d+(//.//d+)?$");
//							Matcher matcher = p.matcher(textValue);
//							if (matcher.matches())
//							{
//								// 是数字当作double处理
//								cell.setCellValue(Double.parseDouble(textValue));
//							}
//							else
//							{
//								HSSFRichTextString richString = new HSSFRichTextString(
//										textValue);
//							/*	HSSFFont font3 = workbook.createFont();
//								font3.setColor(HSSFColor.BLUE.index);
//								richString.applyFont(font3);*/
//								cell.setCellValue(richString);
//							}
//						}
//
//					}
//				}
//				catch (SecurityException e)
//				{
//					e.printStackTrace();
//				}
//				catch (NoSuchMethodException e)
//				{
//					e.printStackTrace();
//				}
//				catch (IllegalArgumentException e)
//				{
//					e.printStackTrace();
//				}
//				catch (IllegalAccessException e)
//				{
//					e.printStackTrace();
//				}
//				catch (InvocationTargetException e)
//				{
//					e.printStackTrace();
//				}
//				finally
//				{
//					// 清理资源
//
//				}
//			}
//		}
//		try
//		{
//			workbook.write(out);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//
//	/**
//	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
//	 *
//	 * @param title
//	 *            表格标题名
//	 * @param headers
//	 *            表格属性列名数组
//	 * @param dataset
//	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
//	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
//	 * @param out
//	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
//	 * @param pattern
//	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
//	 */
//	@SuppressWarnings("unchecked")
//	public void exportExcel(String title, String[] headers,
//			Collection<T> dataset, OutputStream out, String pattern, String[] filedNames)
//	{
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		// 生成一个表格
//		HSSFSheet sheet = workbook.createSheet(title);
//		// 设置表格默认列宽度为15个字节
//		sheet.setDefaultColumnWidth((short) 15);
//		// 生成一个样式
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 生成一个字体
//		HSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.VIOLET.index);
//		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 把字体应用到当前的样式
//		style.setFont(font);
//		// 生成并设置另一个样式
//		HSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		// 生成另一个字体
//		HSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//		// 把字体应用到当前的样式
//		style2.setFont(font2);
//
//		// 声明一个画图的顶级管理器
//		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//		// 定义注释的大小和位置,详见文档
//		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//				0, 0, 0, (short) 4, 2, (short) 6, 5));
//		// 设置注释内容
//		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//      //	comment.setAuthor("leno");
//
//		// 产生表格标题行
//		HSSFRow row = sheet.createRow(0);
//
//		for (short i = 0; i < headers.length; i++)
//		{
//			HSSFCell cell = row.createCell(i);
//			cell.setCellStyle(style);
//			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//
//		// 遍历集合数据，产生数据行
//		Iterator<T> it = dataset.iterator();
//		int index = 0;
//		while (it.hasNext())
//		{
//			index++;
//			row = sheet.createRow(index);
//			T t = (T) it.next();
//			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//			Field[] fields = t.getClass().getDeclaredFields();
//			/*for (short i = 0; i < fields.length; i++)
//			{*/
//			for(int i=0; i<filedNames.length; i++)
//			{
//				HSSFCell cell = row.createCell(i);
//				cell.setCellStyle(style2);
//				/*Field field = fields[i];
//				String fieldName = field.getName();*/
//
//				String fieldName = filedNames[i];
//
//				/*if(retrieve(fields, fieldName)>-1)
//				{*/
//					String getMethodName = "get"
//							+ fieldName.substring(0, 1).toUpperCase()
//							+ fieldName.substring(1);
//					try
//					{
//						Class tCls = t.getClass();
//						Method getMethod = tCls.getMethod(getMethodName,
//								new Class[]
//								{});
//						Object value = getMethod.invoke(t, new Object[]
//						{});
//						// 判断值的类型后进行强制类型转换
//						String textValue = null;
//
//						if (value instanceof Boolean)
//						{
//							boolean bValue = (Boolean) value;
//							textValue = "男";
//							if (!bValue)
//							{
//								textValue = "女";
//							}
//						}
//						else if (value instanceof Date)
//						{
//							Date date = (Date) value;
//							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//							textValue = sdf.format(date);
//						}
//						else if (value instanceof byte[])
//						{
//							// 有图片时，设置行高为60px;
//							row.setHeightInPoints(60);
//							// 设置图片所在列宽度为80px,注意这里单位的一个换算
//							sheet.setColumnWidth(i, (short) (35.7 * 80));
//							// sheet.autoSizeColumn(i);
//							byte[] bsValue = (byte[]) value;
//							HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
//									1023, 255, (short) 6, index, (short) 6, index);
//							anchor.setAnchorType(2);
//							patriarch.createPicture(anchor, workbook.addPicture(
//									bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
//						}
//						else
//						{
//							// 其它数据类型都当作字符串简单处理
//							textValue = value!=null?value.toString():"";
//						}
//						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//						if (textValue != null)
//						{
//							if(fieldName.indexOf("HTTP")>-1 )
//							{
//								String[] Strs = textValue.split(",");
//								HSSFCellStyle hlink_style = workbook.createCellStyle();
//								hlink_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//								hlink_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//								hlink_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//								hlink_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//								hlink_style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//								hlink_style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//								hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//								hlink_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//								HSSFFont hlink_font = workbook.createFont();
//							    hlink_font.setUnderline(HSSFFont.U_SINGLE);
//							    hlink_font.setColor(HSSFColor.BLUE.index);
//							    hlink_style.setFont(hlink_font);
//								for(short j=0; j<5; j++)
//								{
//									HSSFCell cellChild = row.createCell(i+j);
//									if(Strs.length>j && textValue.replace(" ", "").length()>0)
//									{
//										// sheet.ungroupColumn(i, i+j);
//
//										cellChild.setCellValue("图片"+(j+1));
//
//									    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
//
//									    link.setAddress(Strs[j]);
//
//									    cellChild.setHyperlink(link);
//									    cellChild.setCellStyle(hlink_style);
//									}/*else
//										cellChild.setCellStyle(style2);*/
//								}
//							}else
//							{
//								Pattern p = Pattern.compile("^//d+(//.//d+)?$");
//								Matcher matcher = p.matcher(textValue);
//								if (matcher.matches())
//								{
//									// 是数字当作double处理
//									cell.setCellValue(Double.parseDouble(textValue));
//								}
//								else
//								{
//									HSSFRichTextString richString = new HSSFRichTextString(
//											textValue);
//								/*	HSSFFont font3 = workbook.createFont();
//									font3.setColor(HSSFColor.BLUE.index);
//									richString.applyFont(font3);*/
//									cell.setCellValue(richString);
//								}
//							}
//						}
//					}
//					catch (SecurityException e)
//					{
//						e.printStackTrace();
//					}
//					catch (NoSuchMethodException e)
//					{
//						e.printStackTrace();
//					}
//					catch (IllegalArgumentException e)
//					{
//						e.printStackTrace();
//					}
//					catch (IllegalAccessException e)
//					{
//						e.printStackTrace();
//					}
//					catch (InvocationTargetException e)
//					{
//						e.printStackTrace();
//					}
//					finally
//					{
//						// 清理资源
//
//					}
//				/*}*/
//
//			}
//		}
//		try
//		{
//			workbook.write(out);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 检索字符串在字符串数组中的索引值
//	 * @param sttParams
//	 * @param param
//	 * @return
//	 */
//	public int retrieve(String[] sttParams, String param){
//		int index = -1;
//		for(int i=0; i<sttParams.length; i++)
//		{
//			if(param.equals(sttParams[i]))
//				index = i;
//		}
//		return index;
//	}
//
//}
