//package com.cxy.util;
//
//import java.beans.IntrospectionException;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//
//import com.vsvz.base.AppCtx;
//import com.vsvz.basesystem.product.proinfo.dao.IExcelBatchProductDao;
//import com.vsvz.constant.ParseConstans;
//import com.vsvz.model.product;
//import com.vsvz.model.sysuser;
//
//
///**
// * 解析excel 工具类
// * @author PCCW
// *
// */
//@SuppressWarnings("rawtypes")
//public class ParseExcelUtil {
//	IExcelBatchProductDao dao = (IExcelBatchProductDao)AppCtx.getBean("excelbatchDao");
//	public  FileInputStream fis ;
//	public HSSFWorkbook workBook;
//	public HSSFSheet sheet;
//	public ParseXMLUtil parseXmlUtil;
//	public StringBuffer errorString;
//
//	/**当前实体类的code**/
//	public String curEntityCode;
//	/**表头map对象：key:entityCode, value:headMap(index,headTitle)**/
//	public Map curEntityHeadMap ;
//
//	/**字段的必填：key:entityCode+headTitle, value:true(必填),false(不必填)**/
//	public Map curEntityColRequired;
//
//	/**存放每一行的数据**/
//	public  List listDatas ;
//
//
//	public ParseExcelUtil(File excelFile,File xmlFile,String rootname){
//		try {
//			if(excelFile == null){
//				throw new FileNotFoundException();
//			}
//
//		   fis = new FileInputStream(excelFile);
//		   workBook = new HSSFWorkbook(fis);
//		   parseXmlUtil = new ParseXMLUtil(xmlFile,rootname);
//		   errorString = new StringBuffer();
//		   readExcelData();
//
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	/**开始从excel读取数据**/
//	public void readExcelData(){
//		int sheetSize = workBook.getNumberOfSheets();
//		for(int i=0;i<sheetSize;i++){
//			sheet = workBook.getSheetAt(i);
//
//			String entityName = workBook.getSheetName(i);
//			readSheetData(sheet,entityName);
//		}
//
//	}
//
//	/**读每个sheet页的数据**/
//	public void readSheetData(HSSFSheet sheet,String entityName){
//
//		   int rowNumbers = sheet.getPhysicalNumberOfRows();
//		   Map ent = (Map) parseXmlUtil.getEntityMap().get(entityName);
//		   this.setCurEntityCode((String) ent.get("code"));
//		   if(rowNumbers == 0){
//			   System.out.println("================excel中数据为空！");
//			   errorString.append(ParseConstans.ERROR_EXCEL_NULL);
//		   }
//			List colList = (List) parseXmlUtil.getColumnListMap().get(entityName);
//			int xmlRowNum = colList.size();
//		   HSSFRow excelRow = sheet.getRow(0);
//		   int excelFirstRow = excelRow.getFirstCellNum();
//		   int excelLastRow = excelRow.getLastCellNum();
//		   if(xmlRowNum  != (excelLastRow-excelFirstRow)){
//			     System.out.println("==================xml列数与excel列数不相符，请检查");
//			     errorString.append(ParseConstans.ERROR_EXCEL_COLUMN_NOT_EQUAL);
//		   }
//		  readSheetHeadData(sheet);
//
//		   readSheetColumnData(sheet,entityName);
//
//
//
//	   }
//
//   /**读取sheet页中的表头信息**/
//   @SuppressWarnings({ "unchecked", "static-access"})
//	public void readSheetHeadData(HSSFSheet sheet){
//
//		   Map headMap = new HashMap();
//		   curEntityHeadMap = new HashMap();
//		   curEntityColRequired = new HashMap();
//		   HSSFRow excelheadRow = sheet.getRow(0);
//		   int excelLastRow = excelheadRow.getLastCellNum();
//		   String headTitle = "";
//		   for(int i=0;i<excelLastRow;i++){
//			   HSSFCell cell = excelheadRow.getCell(i);
//			   headTitle = this.getStringCellValue(cell).trim();
//			   if(headTitle.endsWith("*")){
//				   curEntityColRequired.put(this.getCurEntityCode()+"_"+headTitle,true);
//			   }else{
//				   curEntityColRequired.put(this.getCurEntityCode()+"_"+headTitle,false);
//			   }
//			   headMap.put(i, headTitle);
//		   }
//		   curEntityHeadMap.put(this.getCurEntityCode(), headMap);
//	   }
//
//   /**读取sheet页里面的数据**/
//   @SuppressWarnings({ "unchecked", "static-access" })
//public void readSheetColumnData(HSSFSheet sheet,String entityName){
//
//
//	   HSSFRow excelheadRow = sheet.getRow(0);
//	   int excelLastcell = excelheadRow.getLastCellNum();   //excel总列数
//	   int excelRowNum = sheet.getLastRowNum();  //excel总行数
//	   Map headMap = (Map) this.getCurEntityHeadMap().get(this.getCurEntityCode());
//	   Map colMap = parseXmlUtil.getColumnMap();
//	   listDatas =new ArrayList();
//
//	   for(int i=1;i<excelRowNum+1;i++){//行循环
//		   HSSFRow columnRow = sheet.getRow(i);
//		   if(columnRow != null){
//			   Map curRowCellMap = new HashMap();
//			   for(int j =0; j<excelLastcell;j++){ //列循环
//				   int cout =  headMap.get(j).toString().indexOf("*");
//				   String headTitle ="";
//				   if(cout == -1){
//					  headTitle = headMap.get(j).toString();
//				   }else{
//					   headTitle =  headMap.get(j).toString().substring(0, cout);
//				   }
//				   Map curColMap =  (Map) colMap.get(entityName+"_"+headTitle);
//
//				   String curColCode = (String) curColMap.get("code");
//				   String curColType = (String) curColMap.get("type");
//				   HSSFCell colCell = columnRow.getCell(j);
//				   String value =this.getStringCellValue(colCell);
//				//   System.out.println("当前数据的值=------》"+value);
//				   if(value != null){
//					   value = value.trim();
//				   }
//				   String xmlColType = (String) curColMap.get("type");
//				   if(xmlColType.equals("int")){
//					   int   intVal = Integer.valueOf(value);
//					   curRowCellMap.put(curColCode, intVal);  //将这一行的数据以code-value的形式存入map
//				   }else{
//					   // 往数据里面存放值 之前 赢应该存放的关联表的有效值针对不同的表进行不同的操作
//				     // curRowCellMap.put(curColCode, value);
//					   curRowCellMap.put(curColCode, handlerConten(value, curColCode));
//				   }
//				   /**验证cell数据**/
//				   validateCellData(i+1,j+1,colCell,entityName,headTitle,curColType,curColCode);
//			   }
//			   listDatas.add(curRowCellMap);
//		   }
//	   }
//
//	   if(this.getErrorString().length() ==0){//如果没有任何错误，就保存
//		  // saveExcelData(entityName);
//		   HashMap  curcolmap=	 (HashMap)parseXmlUtil.getEntityMap().get(entityName);
//		   String  tableName=(String) curcolmap.get("code");
//		   saveExcelDataObj(tableName);
//		   System.out.println("导入数据成功！");
//	   }else{
//		   //清理所有的缓存clearMap();现在暂时未清理
//		 //  curEntityHeadMap.clear();
//		//   curEntityColRequired.clear();
//
//		   String[] strArr = errorString.toString().split("<br>");
//		   for(String s: strArr){
//			   System.out.println(s);
//		   }
//
//	   }
//
//
//   }
//   /**验证单元格数据**/
//   @SuppressWarnings("static-access")
//public void validateCellData(int curRow,int curCol,HSSFCell colCell,String entityName,String headName,String curColType,String  curColCode){
//	//得到当前的表明
//	   HashMap  curcolmap=	 (HashMap)parseXmlUtil.getEntityMap().get(entityName);
// /*  System.out.println("当行值=------》"+entityName);
//	 System.out.println("当行值=------》"+headName);
//	 System.out.println("当行值=------》"+curColType);
//
//	 System.out.println("当行值=------》"+curcolmap.get("code"));
//	 System.out.println("当行值=------》"+curColCode);*/
//// System.out.println("------------------------------------");
//	 String  tableName=(String) curcolmap.get("code");
//
//	   List rulList = (List) parseXmlUtil.getColumnRulesMap().get(entityName+"_"+headName);
//
//	   if(rulList != null && rulList.size()>0){
//
//		   for(int i=0 ; i<rulList.size() ; i++){
//			   Map rulM = (Map) rulList.get(i);
//			   String rulName = (String) rulM.get("name");
//
//			   String rulMsg = (String) rulM.get("message");
//
//			   String cellValue = this.getStringCellValue(colCell);
//			   if(cellValue!=null){
//				   cellValue= cellValue.trim();
//			   }
//		//  System.out.println("第"+curRow+"行,第"+curCol+"列 !当前数据的值=------》"+rulName);
//			   if(rulName.equals(ParseConstans.RULE_NAME_NULLABLE)){
//
//				   if(cellValue == null ||cellValue.equals("")){
//
//					 // System.out.println("第"+curRow+"行,第"+curCol+"列:"+rulMsg+"<br>");
//					   errorString.append("第"+curRow+"行,第"+curCol+"列:("+curColCode+")"+rulMsg+"<br>");
//				   }
//			   }else if(rulName.equals(ParseConstans.RULE_NAME_UNIQUE)) {
//				   //////这里写其他的验证规则。。。
//				   if(tableName.equals("product")){
//
//					   if(cellValue!=null && dao.isExistFiledValue(cellValue,tableName,curColCode)){
//						   errorString.append("第"+curRow+"行,第"+curCol+"列:("+curColCode+")"+rulMsg+"<br>");
//					   }
//
//				   }
//
//
//			   }else if(rulName.equals(ParseConstans.RULE_NAME_ISNOTEXSIT)){
//				// 这里是对关联表的数据进行验证 存在与否  根据不同的表的上传进行判断
//				 if(tableName.equals("product")){
//				 // 产品表的关联子段的格式判断 拿出每一个关联字段进行判断
//					if(cellValue!=null && handlerConten(cellValue,curColCode).equals("")) {
//
//						errorString.append("第"+curRow+"行,第"+curCol+"列:("+curColCode+")"+rulMsg+"<br>");
//					}
//
//				   }
//				   if(tableName.equals("sys_user")){
//
//
//				  }
//
//
//			   }else if(rulName.equals(ParseConstans.RULE_NAME_NUM)){
//
//		      	  try
//	              {
//	                 Double.valueOf(cellValue) ;
//	              }
//	              catch(Exception e)
//	              {
//	            	  errorString.append("第"+curRow+"行,第"+curCol+"列:("+curColCode+")"+rulMsg+"<br>");
//	              }
//		      	}else if(rulName.equals(ParseConstans.RULE_NAME_ISNOTMOTH)){
//		      		if(cellValue!=null &&(Double.valueOf(cellValue) > 13 || Double.valueOf(cellValue) < 1)){
//		      			errorString.append("第"+curRow+"行,第"+curCol+"列:("+curColCode+")"+rulMsg+"<br>");
//		      		}
//
//				}else  if(rulName.equals(ParseConstans.RULE_NAME_ISYESORNOT)){
//					if(cellValue!=null && !(cellValue.equals("是")|| cellValue.equals("否"))){
//						errorString.append("第"+curRow+"行,第"+curCol+"列:("+curColCode+")"+rulMsg+"<br>");
//
//					}
//
//				}
//		   }
//
//	   }
//
//   }
//   /**
//    *
//    * @param curmap  参数表中的数据存放到map 中
//    * @param content xls单元格数据
//    * @return
//    */
//   public   String  handlerConten(String  content,String curvaluecode){
//
//
//
//	   String catetype= getCateTypeSql(curvaluecode);
//	    if(!catetype.equals("")){
//		   // catetype不为空的话
//		   if(catetype.equals("yesorno")){
//				   if(content!=null && content.equals("是")){
//			    		 return "1";
//			    	 }else if(content!=null && content.equals("否")) {
//						return "0";
//					}else {
//						return content;
//					}
//
//		   }else{
//				   Map  curmap =dao.getMap(catetype);
//
//				     if(curmap.containsKey(content)){
//
//				    	return  curmap.get(content)+"";
//				      }else {
//						return  "";
//					 }
//		   }
//
//
//	       }else{
//	    	   // catetype  ② 没有验证规则的字段
//
//	    	   return content;
//		    }
//
//
//	}
//
//
//
//   /**
//    *
//    * @param curvaluecode 当前列的列名所对应的code
//    * @return 这一列的code对应参数表的参数类型
//    */
//   public String getCateTypeSql(String curvaluecode) {
//
//	   if(curvaluecode.equals("big_cate_code")||curvaluecode.equals("mid_cate_code") ||curvaluecode.equals("sma_cate_code")){
//			return  "select cate_code,cate_id from prod_cate";
//	   }
//
//	   if(curvaluecode.equals("size_code")){
//
//			return  "select group_code,size_group_id from size_group ";
//		 }
//	   if(curvaluecode.equals("prod_color_id")){
//
//			return  "select color_code, prod_color_id from prod_color";
//		 }
//	 if(curvaluecode.equals("brade_code")){
//
//		return  "select param_code,param_id from sys_param  where param_cate=1";
//	 }
//     if(curvaluecode.equals("serial_no")){
//
//    		return  "select param_code,param_id from sys_param  where param_cate=2";
//	 }
//    if(curvaluecode.equals("gender_code")){
//
//		   return  "select param_code,param_id from sys_param  where param_cate=4";
//	 }
//      if(curvaluecode.equals("season_code")){
//	   return "select param_code,param_id from sys_param  where param_cate=5";
//      }
//
//      if(curvaluecode.equals("up_down")){
//    		  return "select param_code,param_id from sys_param  where param_cate=3";
//      }
//      if(curvaluecode.equals("year")){
//
//    	  return "select param_code,param_id from sys_param  where param_cate=6";
//      }
//      if(curvaluecode.equals("ab")){
//
//    		 return "select param_code,param_id from sys_param  where param_cate=7";
//       }
//      if(curvaluecode.equals("electric")||curvaluecode.equals("policy_code")||curvaluecode.equals("group_flag")||curvaluecode.equals("pop")){
//
// 		 return "yesorno";
//      }
//
//	   return  "";
//
//   }
//
//
//   /**保存excel里面的数据**/
//   @SuppressWarnings("unchecked")
//public void saveExcelDataObj(String entityName){
//	   /**批量保存数据**/
//	   for(int i = 0 ; i<this.getListDatas().size();i++){
//		   Map excelCol = (Map) this.getListDatas().get(i);  //得到第 i 行的数据
//	//	   User user = new User();
//		   Object object=null;
//		   if(entityName.equals("sys_user")){
//			    object = new sysuser();
//		   }else  if(entityName.equals("product")){
//			   object = new product();
//	     	}
//	  try {
//			Object[] obj =  BeanToMapUtil.convertMapToArry(object.getClass(), excelCol);
//			String[] filedname=(String[])obj[0];
//			String[] filedvalue=(String[])obj[1];
//
//			dao.saveObj(filedname,filedvalue,entityName);
//		} catch (IntrospectionException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//
//	   }
//   }
//
//
//
//   /**
//	 * 获得单元格字符串
//	 * @throws UnSupportedCellTypeException
//	 */
//	public static String getStringCellValue(HSSFCell cell) {
//		if (cell == null){
//			return null;
//		}
//
//		String result = "";
//		switch (cell.getCellType()) {
//			case HSSFCell.CELL_TYPE_BOOLEAN:
//				result = String.valueOf(cell.getBooleanCellValue());
//				break;
//			case HSSFCell.CELL_TYPE_NUMERIC:
//				if (HSSFDateUtil.isCellDateFormatted(cell)) {
//					java.text.SimpleDateFormat TIME_FORMATTER = new java.text.SimpleDateFormat(
//							"yyyy-MM-dd");
//					result = TIME_FORMATTER.format(cell.getDateCellValue());
//				}
//				else{
//					double doubleValue = cell.getNumericCellValue();
//					result = "" + doubleValue;
//				}
//				break;
//			case HSSFCell.CELL_TYPE_STRING:
//				if (cell.getRichStringCellValue() == null){
//					result = null;
//				}
//				else{
//					result = cell.getRichStringCellValue().getString();
//				}
//				break;
//			case HSSFCell.CELL_TYPE_BLANK:
//				result = null;
//				break;
//			case HSSFCell.CELL_TYPE_FORMULA:
//				try{
//					result = String.valueOf(cell.getNumericCellValue());
//		        }catch(Exception e){
//		        	result = cell.getRichStringCellValue().getString();
//		        }
//				break;
//			default:
//				result = "";
//		}
//
//		return result;
//	}
//	/**主方法**/
//	public static void main(String[] args) {
//	/*
//		File excelFile = new File("src/user.xls");
//		File xmlFile = new File("src/user.xml");
//        new ParseExcelUtil(excelFile,xmlFile,"用户表");	*/
//
//		File excelFile = new File("src/product.xls");
//		File xmlFile = new File("src/product.xml");
//        new ParseExcelUtil(excelFile,xmlFile,"产品表");
//
//	}
//
//
//
//	public String getCurEntityCode() {
//		return curEntityCode;
//	}
//	public void setCurEntityCode(String curEntityCode) {
//		this.curEntityCode = curEntityCode;
//	}
//	public Map getCurEntityHeadMap() {
//		return curEntityHeadMap;
//	}
//	public void setCurEntityHeadMap(Map curEntityHeadMap) {
//		this.curEntityHeadMap = curEntityHeadMap;
//	}
//	public ParseXMLUtil getParseXmlUtil() {
//		return parseXmlUtil;
//	}
//	public void setParseXmlUtil(ParseXMLUtil parseXmlUtil) {
//		this.parseXmlUtil = parseXmlUtil;
//	}
//	public Map getCurEntityColRequired() {
//		return curEntityColRequired;
//	}
//	public void setCurEntityColRequired(Map curEntityColRequired) {
//		this.curEntityColRequired = curEntityColRequired;
//	}
//
//	public List getListDatas() {
//		return listDatas;
//	}
//	public void setListDatas(List listDatas) {
//		this.listDatas = listDatas;
//	}
//	public StringBuffer getErrorString() {
//		return errorString;
//	}
//	public void setErrorString(StringBuffer errorString) {
//		this.errorString = errorString;
//	}
//
//
//
//
//}
