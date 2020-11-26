//package com.cxy.util;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.sql.Date;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.vsvz.base.excel.IXSSFRowFormat;
//
//public class ReadExcel{
//
//
//	 public static void main(String[] args) throws Exception {
//
//		   File file = new File("C:/Users/Administrator/Desktop/user.xls");
//
//	       String[][] result = getData(file, 1);
//
//	       int rowLength = result.length;
//
//	       for(int i=0;i<rowLength;i++) {
//
//	           for(int j=0;j<result[i].length;j++) {
//
//	              System.out.print(result[i][j]+"\t\t");
//
//	           }
//
//	           System.out.println();
//
//	       }
//
//
//
//	    }
//
//	    /**
//
//	     * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
//
//	     * @param file 读取数据的源Excel
//
//	     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
//
//	     * @return 读出的Excel中数据的内容
//
//	     * @throws FileNotFoundException
//
//	     * @throws IOException
//
//	     */
//
//	    public static String[][] getData(File file, int ignoreRows)
//
//	           throws FileNotFoundException, IOException {
//
//	       List<String[]> result = new ArrayList<String[]>();
//
//	       int rowSize = 0;
//
//	       BufferedInputStream in = new BufferedInputStream(new FileInputStream(
//
//	              file));
//
//	       // 打开HSSFWorkbook
//
//	       POIFSFileSystem fs = new POIFSFileSystem(in);
//
//	       HSSFWorkbook wb = new HSSFWorkbook(fs);
//
//	       HSSFCell cell = null;
//
//	       for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//
//	           HSSFSheet st = wb.getSheetAt(sheetIndex);
//
//	           // 第一行为标题，不取
//
//	           for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
//
//	              HSSFRow row = st.getRow(rowIndex);
//
//	              if (row == null) {
//
//	                  continue;
//
//	              }
//
//	              int tempRowSize = row.getLastCellNum() + 1;
//
//	              if (tempRowSize > rowSize) {
//
//	                  rowSize = tempRowSize;
//
//	              }
//
//	              String[] values = new String[rowSize];
//
//	              Arrays.fill(values, "");
//
//	              boolean hasValue = false;
//
//	              for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
//
//	                  String value = "";
//
//	                  cell = row.getCell(columnIndex);
//
//	                  if (cell != null) {
//
//	                     // 注意：一定要设成这个，否则可能会出现乱码
//
//	                     switch (cell.getCellType()) {
//
//	                     case HSSFCell.CELL_TYPE_STRING:
//
//	                         value = cell.getStringCellValue();
//
//	                         break;
//
//	                     case HSSFCell.CELL_TYPE_NUMERIC:
//
//	                         if (HSSFDateUtil.isCellDateFormatted(cell)) {
//
//	                            Date date = null;
//
//	                            if (date != null) {
//	                            	 value = "";
//
//	                            } else {
//
//	                                value = "";
//
//	                            }
//
//	                         } else {
//
//	                            value = new DecimalFormat("0").format(cell
//
//	                                   .getNumericCellValue());
//
//	                         }
//
//	                         break;
//
//	                     case HSSFCell.CELL_TYPE_FORMULA:
//
//	                         // 导入时如果为公式生成的数据则无值
//
//	                         if (!cell.getStringCellValue().equals("")) {
//
//	                            value = cell.getStringCellValue();
//
//	                         } else {
//
//	                            value = cell.getNumericCellValue() + "";
//
//	                         }
//
//	                         break;
//
//	                     case HSSFCell.CELL_TYPE_BLANK:
//
//	                         break;
//
//	                     case HSSFCell.CELL_TYPE_ERROR:
//
//	                         value = "";
//
//	                         break;
//
//	                     case HSSFCell.CELL_TYPE_BOOLEAN:
//
//	                         value = (cell.getBooleanCellValue() == true ? "Y"
//
//	                                : "N");
//
//	                         break;
//
//	                     default:
//
//	                         value = "";
//
//	                     }
//
//	                  }
//
//	                  if (columnIndex == 0 && value.trim().equals("")) {
//
//	                     break;
//
//	                  }
//
//	                  values[columnIndex] = rightTrim(value);
//
//	                  hasValue = true;
//
//	              }
//
//
//
//	              if (hasValue) {
//
//	                  result.add(values);
//
//	              }
//
//	           }
//
//	       }
//
//	       in.close();
//
//	       String[][] returnArray = new String[result.size()][rowSize];
//
//	       for (int i = 0; i < returnArray.length; i++) {
//
//	           returnArray[i] = (String[]) result.get(i);
//
//	       }
//
//	       return returnArray;
//
//	    }
//
//	    /**
//	     * 读取excel文件并转换为指定对象
//	     * @param file excel文件
//	     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
//	     * @param clz 转换的类型
//	     * @param fileName 文件名
//	     * @return
//	     * @throws Exception
//	     */
//	    public<T> List<T> getFormatData(File file, int ignoreRows, Class<T> clz, String fileName) throws Exception {
//	    	// 获取文件类型并转换为小写
//	    	String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//	    	if("xlsx".equals(fileExt)) {
//	    		return getXlsxFormatData(file, ignoreRows, clz);
//	    	} else if("xls".equals(fileExt)) {
//	    		return getXlsFormatData(file, ignoreRows, clz);
//	    	} else
//	    		return null;
//	    }
//
//	    /**
//	     * 读取excel文件并转换为指定对象(xlsx)
//	     * @param file excel文件
//	     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
//	     * @param clz 转换的类型
//	     * @return
//	     * @throws FileNotFoundException
//	     * @throws IOException
//	     * @throws InvalidFormatException
//	     */
//	    public<T> List<T> getXlsxFormatData(File file, int ignoreRows, Class<T> clz) throws FileNotFoundException, IOException, InvalidFormatException {
//	    	List<T> result = new ArrayList<T>();
//	    	int rowSize = 0;
//	    	// HSSFWorkbook wb = new HSSFWorkbook(fs);
//	    	XSSFWorkbook wb = new XSSFWorkbook(file);
//	    	for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//	    		XSSFSheet st = wb.getSheetAt(sheetIndex);
//	    		// 第一行为标题，不取
//	    		for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
//	    			XSSFRow row = st.getRow(rowIndex);
//	    			if (row == null) {
//	    				continue;
//	    			}
//	    			int tempRowSize = row.getLastCellNum() + 1;
//	    			if (tempRowSize > rowSize) {
//	    				rowSize = tempRowSize;
//	    			}
//	    			T t = format(row, clz);
//	    			if(t != null)
//	    				result.add(t);
//	    		}
//	    	}
//	    	return result;
//	    }
//
//	    /**
//	     * 读取excel文件并转换为指定对象(xls)
//	     * @param file excel文件
//	     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
//	     * @param clz 转换的类型
//	     * @return
//	     * @throws FileNotFoundException
//	     * @throws IOException
//	     * @throws InvalidFormatException
//	     */
//	    public<T> List<T> getXlsFormatData(File file, int ignoreRows, Class<T> clz) throws FileNotFoundException, IOException, InvalidFormatException {
//	    	List<T> result = new ArrayList<T>();
//	    	int rowSize = 0;
//	    	// HSSFWorkbook wb = new HSSFWorkbook(fs);
//	    	BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
//	    	POIFSFileSystem fs = new POIFSFileSystem(in);
//	    	HSSFWorkbook wb = new HSSFWorkbook(fs);
//	    	for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//	    		HSSFSheet st = wb.getSheetAt(sheetIndex);
//	    		// 第一行为标题，不取
//	    		for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
//	    			HSSFRow row = st.getRow(rowIndex);
//	    			if (row == null) {
//	    				continue;
//	    			}
//	    			int tempRowSize = row.getLastCellNum() + 1;
//	    			if (tempRowSize > rowSize) {
//	    				rowSize = tempRowSize;
//	    			}
//	    			T t = format(row, clz);
//	    			if(t != null)
//	    				result.add(t);
//	    		}
//	    	}
//	    	return result;
//	    }
//
//	    /**
//	     * 读取excel文件并转换为指定对象(xlsx)
//	     * @param inputStream excel文件输入流
//	     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
//	     * @param clz 转换的类型
//	     * @return
//	     * @throws FileNotFoundException
//	     * @throws IOException
//	     * @throws InvalidFormatException
//	     */
//	    public<T> List<T> getXlsxFormatData(InputStream inputStream, int ignoreRows, Class<T> clz, IXSSFRowFormat format) throws FileNotFoundException, IOException, InvalidFormatException {
//	    	List<T> result = new ArrayList<T>();
//	    	// int rowSize = 0;
//	    	XSSFWorkbook wb = new XSSFWorkbook(inputStream);
//	    	for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//	    		XSSFSheet st = wb.getSheetAt(sheetIndex);
//	    		// 第一行为标题，不取
//	    		for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
//	    			XSSFRow row = st.getRow(rowIndex);
//	    			if (row == null) {
//	    				continue;
//	    			}
//	    			/*int tempRowSize = row.getLastCellNum() + 1;
//	    			if (tempRowSize > rowSize) {
//	    				rowSize = tempRowSize;
//	    			}*/
//	    			// T t = format(row, clz);
//	    			T t = (T) format.format(row, clz);
//	    			if(t != null)
//	    				result.add(t);
//	    		}
//	    	}
//	    	return result;
//	    }
//
//	    /**
//	     * 读取excel文件并转换为指定对象(xls)
//	     * @param inputStream excel文件输入流
//	     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
//	     * @param clz 转换的类型
//	     * @return
//	     * @throws FileNotFoundException
//	     * @throws IOException
//	     * @throws InvalidFormatException
//	     */
//	    public<T> List<T> getXlsFormatData(InputStream inputStream, int ignoreRows, Class<T> clz) throws FileNotFoundException, IOException, InvalidFormatException {
//	    	List<T> result = new ArrayList<T>();
//	    	int rowSize = 0;
//	    	BufferedInputStream in = new BufferedInputStream(inputStream);
//	    	POIFSFileSystem fs = new POIFSFileSystem(in);
//	    	HSSFWorkbook wb = new HSSFWorkbook(fs);
//	    	for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//	    		HSSFSheet st = wb.getSheetAt(sheetIndex);
//	    		// 第一行为标题，不取
//	    		for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
//	    			HSSFRow row = st.getRow(rowIndex);
//	    			if (row == null) {
//	    				continue;
//	    			}
//	    			int tempRowSize = row.getLastCellNum() + 1;
//	    			if (tempRowSize > rowSize) {
//	    				rowSize = tempRowSize;
//	    			}
//	    			T t = format(row, clz);
//	    			if(t != null)
//	    				result.add(t);
//	    		}
//	    	}
//	    	return result;
//	    }
//
//	    private<T> T format(XSSFRow row, Class<T> clz) {
//	    	if(row == null) {
//    			return null;
//    		}
//	    	try {
//	    		T t = clz.newInstance();
//				Field[] fields = t.getClass().getDeclaredFields();
//				for(int i=0; i<fields.length; i++)
//				{
//					Field field = fields[i];
//					String fieldName = field.getName();
//					String setMethodName = "set"
//							+ fieldName.substring(0, 1).toUpperCase()
//							+ fieldName.substring(1);
//					Method setMethod = clz.getMethod(setMethodName, new Class[]{field.getType()});
//					String value = null;
//					XSSFCell cell = row.getCell(i);
//					if (cell != null) {
//    					// 注意：一定要设成这个，否则可能会出现乱码
//    					switch (cell.getCellType()) {
//    						case XSSFCell.CELL_TYPE_STRING:
//    							value = cell.getStringCellValue();
//    							break;
//    						case XSSFCell.CELL_TYPE_NUMERIC:
//    							/*if (HSSFDateUtil.isCellDateFormatted(cell)) {
//    								Date date = null;
//		                            if (date != null) {
//		                            	 value = "";
//		                            } else {
//		                                value = "";
//		                            }
//    							} else {
//    								value = cell.getNumericCellValue()+"";
//    							}*/
//    							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//    							value = cell.getStringCellValue();
//    							break;
//    						case XSSFCell.CELL_TYPE_FORMULA:
//    							// 导入时如果为公式生成的数据则无值
//    							/*if (!cell.getStringCellValue().equals("")) {
//    								value = cell.getStringCellValue();
//    							} else {
//    								value = cell.getNumericCellValue() + "";
//    							}*/
//    							value = cell.getCellFormula();
//    							break;
//    						case XSSFCell.CELL_TYPE_BLANK:
//    							break;
//    						case XSSFCell.CELL_TYPE_ERROR:
//    							value = "";
//    							break;
//    						case XSSFCell.CELL_TYPE_BOOLEAN:
//    							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
//    							break;
//    						default:
//    							value = "";
//    					}
//
//    					if (i == 0 && (value == null || value.trim().equals(""))) {
//    						return null;
//	    				}
//    				} else {
//    					if(i == 0) {
//    						return null;
//    					}
//    				}
//					// 类型转换
//					if(value != null && !"".equals(value)) {
//						if(field.getType() == Integer.class) {
//							BigDecimal bd = new BigDecimal(value);
//							setMethod.invoke(t, bd.intValue());
//						} else if(field.getType() == Date.class) {
//							setMethod.invoke(t, DateUtil.formatStringToUtilDate(value));
//						} else if(field.getType() == Double.class) {
//							setMethod.invoke(t, Double.valueOf(value));
//						} else if(field.getType() == Long.class) {
//							setMethod.invoke(t, Long.parseLong(value));
//						} else {
//							setMethod.invoke(t, value);
//						}
//					} else {
//						setMethod.invoke(t, value);
//					}
//				}
//				return t;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	    	return null;
//	    }
//
//	    private<T> T format(HSSFRow row, Class<T> clz) {
//	    	if(row == null) {
//    			return null;
//    		}
//	    	try {
//	    		T t = clz.newInstance();
//				Field[] fields = t.getClass().getDeclaredFields();
//				for(int i=0; i<fields.length; i++)
//				{
//					Field field = fields[i];
//					String fieldName = field.getName();
//					String setMethodName = "set"
//							+ fieldName.substring(0, 1).toUpperCase()
//							+ fieldName.substring(1);
//					Method setMethod = clz.getMethod(setMethodName, new Class[]{field.getType()});
//					String value = "";
//					HSSFCell cell = row.getCell(i);
//					if (cell != null) {
//    					// 注意：一定要设成这个，否则可能会出现乱码
//    					switch (cell.getCellType()) {
//    						case HSSFCell.CELL_TYPE_STRING:
//    							value = cell.getStringCellValue();
//    							break;
//    						case HSSFCell.CELL_TYPE_NUMERIC:
//    							/*if (HSSFDateUtil.isCellDateFormatted(cell)) {
//    								Date date = null;
//		                            if (date != null) {
//		                            	 value = "";
//		                            } else {
//		                                value = "";
//		                            }
//    							} else {
//    								value = new DecimalFormat("0").format(cell.getNumericCellValue());
//    							}*/
//    							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//    							value = cell.getStringCellValue();
//    							break;
//    						case HSSFCell.CELL_TYPE_FORMULA:
//    							// 导入时如果为公式生成的数据则无值
//    							/*if (!cell.getStringCellValue().equals("")) {
//    								value = cell.getStringCellValue();
//    							} else {
//    								value = cell.getNumericCellValue() + "";
//    							}*/
//    							value = cell.getCellFormula();
//    							break;
//    						case HSSFCell.CELL_TYPE_BLANK:
//    							break;
//    						case HSSFCell.CELL_TYPE_ERROR:
//    							value = "";
//    							break;
//    						case HSSFCell.CELL_TYPE_BOOLEAN:
//    							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
//    							break;
//    						default:
//    							value = "";
//    					}
//
//    					if (i == 0 && value.trim().equals("")) {
//    						return null;
//	    				}
//    				}else {
//    					if(i == 0) {
//    						return null;
//    					}
//    				}
//					// 类型转换
//					if(value != null && !"".equals(value)) {
//						if(field.getType() == Integer.class) {
//							BigDecimal bd = new BigDecimal(value);
//							setMethod.invoke(t, bd.intValue());
//						} else if(field.getType() == Date.class) {
//							setMethod.invoke(t, DateUtil.formatStringToUtilDate(value));
//						} else if(field.getType() == Double.class) {
//							setMethod.invoke(t, Double.valueOf(value));
//						} else if(field.getType() == Long.class) {
//							setMethod.invoke(t, Long.parseLong(value));
//						} else {
//							setMethod.invoke(t, value);
//						}
//					} else {
//						if(field.getType() == Integer.class) {
//							BigDecimal bd = new BigDecimal(0);
//							setMethod.invoke(t, bd.intValue());
//						} else if(field.getType() == Date.class) {
//							setMethod.invoke(t, DateUtil.formatStringToUtilDate("1900-01-01 00:00:00"));
//						} else if(field.getType() == Double.class) {
//							setMethod.invoke(t, Double.valueOf(0));
//						} else if(field.getType() == Long.class) {
//							setMethod.invoke(t, Long.valueOf("0"));
//						} else {
//							setMethod.invoke(t, value);
//						}
//					}
//				}
//				return t;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	    	return null;
//	    }
//
//
//	    /**
//
//	     * 去掉字符串右边的空格
//
//	     * @param str 要处理的字符串
//
//	     * @return 处理后的字符串
//
//	     */
//
//	     public static String rightTrim(String str) {
//
//	       if (str == null) {
//
//	           return "";
//
//	       }
//
//	       int length = str.length();
//
//	       for (int i = length - 1; i >= 0; i--) {
//
//	           if (str.charAt(i) != 0x20) {
//
//	              break;
//
//	           }
//
//	           length--;
//
//	       }
//
//	       return str.substring(0, length);
//
//	    }
//
//
//	/**
//	 * 读取excel文件并转换为指定对象(xlsx)
//	 * @param inputStream excel文件输入流
//	 * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
//	 * @param clz 转换的类型
//	 * @return
//	 * @throws FileNotFoundException
//	 * @throws IOException
//	 * @throws InvalidFormatException
//	 */
//	public<T> List<T> getXlsxFormatData(InputStream inputStream, int ignoreRows, Class<T> clz, IXSSFRowFormat format,int sheetIndex,int index) throws FileNotFoundException, IOException, InvalidFormatException {
//		List<T> result = new ArrayList<T>();
//		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
//			XSSFSheet st = wb.getSheetAt(sheetIndex);
//			// 第一行为标题，不取
//			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
//				XSSFRow row = st.getRow(rowIndex);
//				if (row == null) {
//					continue;
//				}
//	    			/*int tempRowSize = row.getLastCellNum() + 1;
//	    			if (tempRowSize > rowSize) {
//	    				rowSize = tempRowSize;
//	    			}*/
//				// T t = format(row, clz);
//				T t = (T) format.format(row, clz,index);
//				if(t != null)
//					result.add(t);
//			}
//		return result;
//	}
//}
