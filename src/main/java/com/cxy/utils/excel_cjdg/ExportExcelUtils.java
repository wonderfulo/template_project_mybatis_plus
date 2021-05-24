package com.cxy.utils.excel_cjdg;

import com.cxy.utils.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * excel导出工具类
 *
 * @author wangpeng
 * @date 2018/6/13
 */
public class ExportExcelUtils {

    /**
     * sheet默认列宽度
     */
    private static final int DEFAULT_COLUMN_WIDTH = 15;

    /**
     * 创建一个新的工作薄
     *
     * @return
     */
    public static Workbook newWorkbookInstance(ExcelVersion version) {
        //TODO 支持SXSSF格式，当文件写入的流特别的大时候，会将内存中数据刷新flush到硬盘中，减少内存的使用量。起到以空间换时间作用，提供效率。
        if (version == ExcelVersion.VERSION_2003) {
            return new HSSFWorkbook();
        }
        return new XSSFWorkbook();
    }

    /**
     * 创建某个工作簿下的sheet表
     *
     * @param workbook 当前工作簿
     * @param sheetName sheet名称
     * @return
     */
    public static Sheet newSheetInstance(Workbook workbook, String sheetName) {
        if (workbook == null) {
            throw new NullPointerException("Workbook is null");
        }
        if (StringUtils.isEmpty(sheetName)) {
            //使用默认sheet name
            return workbook.createSheet();
        }
        return workbook.createSheet(sheetName);
    }

    /**
     * 创建某个工作表下的画图管理器
     *
     * @param sheet
     * @return
     */
    public static Drawing newDrawingInstance(Sheet sheet) {
        if (sheet == null) {
            throw new NullPointerException("Sheet is null");
        }
        return sheet.createDrawingPatriarch();
    }

    /**
     * 创建一个注释
     *
     * @param drawing
     * @param anchor
     * @return
     */
    public static Comment newCommentInstance(Drawing drawing, ClientAnchor anchor) {
        if (drawing == null) {
            throw new NullPointerException("Drawing is null");
        }
        if (anchor == null) {
            throw new NullPointerException("ClientAnchor is null");
        }
        return drawing.createCellComment(anchor);
    }

    /**
     * 创建某个工作簿下的单元格样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle newCellStyleInstance(Workbook workbook) {
        if (workbook == null) {
            throw new NullPointerException("Workbook is null");
        }
        return workbook.createCellStyle();
    }

    /**
     * 创建某个工作簿下的字体样式
     *
     * @param workbook
     * @return
     */
    public static Font newFontInstance(Workbook workbook) {
        if (workbook == null) {
            throw new NullPointerException("Workbook is null");
        }
        return workbook.createFont();
    }

    /**
     * 创建某个sheet表的某一行
     *
     * @param sheet
     * @param rowNum 行编号
     * @return
     */
    public static Row newRowInstance(Sheet sheet, Integer rowNum) {
        if (sheet == null) {
            throw new NullPointerException("Sheet is null");
        }
        if (rowNum == null) {
            throw new NullPointerException("rowNum is null");
        }
        return sheet.createRow(rowNum);
    }

    /**
     * 创建某行的某个单元格
     *
     * @param row
     * @param cellNum 列编号
     * @return
     */
    public static Cell newCellInstance(Row row, Integer cellNum) {
        if (row == null) {
            throw new NullPointerException("Row is null");
        }
        if (cellNum == null) {
            throw new NullPointerException("cellNum is null");
        }
        return row.createCell(cellNum);
    }

    /**
     * 创建一个富文本字符串
     *
     * @param version
     * @param text 字符串内容
     * @return
     */
    public static RichTextString newRichTextStringInstance(ExcelVersion version, String text) {
        if (version == ExcelVersion.VERSION_2003) {
            return new HSSFRichTextString(text);
        }
        return new XSSFRichTextString(text);
    }

    /**
     * 创建一个link类型
     *
     * @param version
     * @param workbook
     * @param linkType {@link Hyperlink} LINK_URL LINK_DOCUMENT LINK_EMAIL LINK_FILE
     * @return
     */
    public static Hyperlink newHyperlinkInstance(ExcelVersion version, Workbook workbook, Integer linkType) {
        if (linkType == null) {
            throw new NullPointerException("linkType is null");
        }
        if (version == ExcelVersion.VERSION_2003) {
            CreationHelper createHelper = workbook.getCreationHelper();
            Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
            return link;
            //return new HSSFHyperlink(linkType);
        }
        return workbook.getCreationHelper().createHyperlink(linkType);
    }

    /**
     * 设置sheet默认属性
     *
     * @param sheet
     * @return
     */
    public static Sheet setSheetDefaultOptions(Sheet sheet) {
        if (sheet == null) {
            throw new NullPointerException("Sheet is null");
        }
        //设置sheet默认列宽度为15个字节
        sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
        return sheet;
    }

    /**
     * 设置表头cell默认属性
     *
     * @param style
     * @return
     */
    public static CellStyle setHeadCellStyleDefaultOptions(CellStyle style) {
        if (style == null) {
            throw new NullPointerException("CellStyle is null");
        }
        //设置背景色
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //设置边框
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        //设置居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        //自动换行
        style.setWrapText(true);
        return style;
    }

    /**
     * 设置数据行cell默认属性
     *
     * @param style
     * @return
     */
    public static CellStyle setDataCellStyleDefaultOptions(CellStyle style) {
        if (style == null) {
            throw new NullPointerException("CellStyle is null");
        }
        //设置背景色
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //设置边框
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        //设置居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //自动换行
        style.setWrapText(true);
        return style;
    }

    /**
     * 设置url的默认属性
     *
     * @param workbook 工作簿
     * @param style 样式
     * @return
     */
    public static CellStyle setUrlCellStyleDefaultOptions(Workbook workbook, CellStyle style) {
        if (workbook == null) {
            throw new NullPointerException("Workbook is null");
        }
        if (style == null) {
            throw new NullPointerException("CellStyle is null");
        }
        //设置默认样式
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //设置字体
        Font font = newFontInstance(workbook);
        font.setUnderline(Font.U_SINGLE);
        font.setColor(HSSFColor.BLUE.index);
        style.setFont(font);
        return style;
    }

    /**
     * 设置表头字体默认属性
     *
     * @param font
     * @return
     */
    public static Font setHeadFontDefaultOptions(Font font) {
        if (font == null) {
            throw new NullPointerException("Font is null");
        }
        //设置默认样式
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return font;
    }

    /**
     * 设置数据行字体默认属性
     *
     * @param font
     * @return
     */
    public static Font setDataFontDefaultOptions(Font font) {
        if (font == null) {
            throw new NullPointerException("Font is null");
        }
        //设置默认样式
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        return font;
    }

    /**
     * 设置样式字体
     *
     * @param style
     * @param font
     * @return
     */
    public static CellStyle setCellStyleFont(CellStyle style, Font font) {
        style.setFont(font);
        return style;
    }

    /**
     * 设置单元格样式
     *
     * @param cell
     * @param style
     * @return
     */
    public static Cell setRowCellStyle(Cell cell, CellStyle style) {
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * 生成bean类型的workbook
     *
     * @param version
     * @param workbook
     * @param sheetName
     * @param condition
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Workbook buildSheetOfBean(ExcelVersion version, Workbook workbook, String sheetName, ExportExcelCondition<T> condition) throws IllegalAccessException {

        if (workbook == null) {
            throw new NullPointerException("Workbook is null");
        }

        if (condition == null) {
            throw new NullPointerException("ExportExcelCondition is null");
        }

        if (condition.getType() != ExportExcelCondition.BEAN) {
            throw new IllegalArgumentException("only support ExportExcelCondition.BEAN");
        }

        //创建一个sheet表
        Sheet sheet = newSheetInstance(workbook, sheetName);
        //使用默认sheet样式
        setSheetDefaultOptions(sheet);
        //使用默认头部样式
        CellStyle headStyle = setHeadCellStyleDefaultOptions(newCellStyleInstance(workbook));
        //使用默认头部字体
        Font headFont = setHeadFontDefaultOptions(newFontInstance(workbook));
        setCellStyleFont(headStyle, headFont);
        //使用默认数据行样式
        CellStyle dataStyle = setDataCellStyleDefaultOptions(newCellStyleInstance(workbook));
        //使用默认数据行字体
        Font dataFont = setDataFontDefaultOptions(newFontInstance(workbook));
        setCellStyleFont(dataStyle, dataFont);

        //生成表头
        String[] headers = condition.getHeaders();
        if (headers != null && headers.length > 0) {
            Row row = newRowInstance(sheet, 0);
            for (int i = 0; i < headers.length; i++) {
                setRowCellStyle(newCellInstance(row, i), headStyle).setCellValue(newRichTextStringInstance(version, headers[i]));
            }
        }
        //生成数据行
        Collection<T> data = condition.getData();
        FieldControlObject[] fieldControlObjects = condition.getFieldControlObjects();
        //有数据才生成
        if (data != null && fieldControlObjects != null && fieldControlObjects.length > 0) {
            int rowNum = 1;
            //遍历数据行
            for (T t : data) {
                Row row = newRowInstance(sheet, rowNum);
                //使用反射动态获取属性，先获取自己和父类的public属性，再获取自己的属性，包含public，protected，private
                Field[] fields = ArrayUtils.addAll(t.getClass().getFields(), t.getClass().getDeclaredFields());
                //遍历字段对象数组
                int colNum = 0;
                for (FieldControlObject fieldControlObject : fieldControlObjects) {
                    //获取字段名
                    String fieldName = fieldControlObject.getFieldName();
                    //根据字段名获取对应的Field对象
                    Field field = getFieldByName(fields, fieldName);
                    //字段名在bean中
                    if (field != null) {
                        //设置数据行样式
                        Cell cell = newCellInstance(row, colNum);
                        cell.setCellStyle(dataStyle);
                        //获取值
                        field.setAccessible(true);
                        Object value = field.get(t);
                        //获取值导出类型
                        ExportExcelCellType fieldType = fieldControlObject.getFieldType();
                        //根据fieldType处理JavaBean类型数据
                        excelBeanHandlerByFieldType(version, workbook, sheet, row, colNum, rowNum, t, fields, fieldControlObject, cell, value, fieldType);
                        //下一列
                        colNum++;
                    } else {
                        throw new IllegalArgumentException("fieldName error");
                    }
                }
                rowNum++;
            }
        }


        return workbook;
    }

    /**
     * 根据fieldType处理JavaBean类型数据
     *
     * @param t 数据对象
     * @param fields 反射field数组
     * @param fieldControlObject javabean字段条件对象
     * @param cell excel单元格
     * @param value javabean字段值
     * @param fieldType excel导出类型
     * @param <T>
     */
    private static <T> void excelBeanHandlerByFieldType(ExcelVersion version, Workbook workbook, Sheet sheet, Row row, int colNum, int rowNum, T t, Field[] fields, FieldControlObject fieldControlObject, Cell cell, Object value, ExportExcelCellType fieldType) {
        FormatterOfBean formatterOfBean = fieldControlObject.getFormatterOfBean();
        switch (fieldType) {
            case RICH_TEXT_STRING:
                //富文本字符串
                if (formatterOfBean != null) {
                    cell.setCellValue(newRichTextStringInstance(version, formatterOfBean.format(value, fields, t)));
                } else {
                    cell.setCellValue(newRichTextStringInstance(version, String.valueOf(value)));
                }
                break;
            case DATE:
                excelDateFieldTypeHandler(fieldControlObject, cell, value);
                break;
            case LINK_URL:
                excelLinkUrlFieldTypeHandler(version, workbook, fieldControlObject, cell, value);
                break;
            case IMG_BYTE:
                //图片字节数组类型
                excelImageByteFieldTypeHandler(workbook, sheet, row, colNum, rowNum, value);
                break;
            case IMG_URL:
                excelImgUrlFieldTypeHandler(workbook, sheet, row, colNum, rowNum, fieldControlObject, value);
                break;
            default:
                throw new UnsupportedOperationException("can not support current ExportExcelCellType");
        }
    }

    /**
     * 生成二维array类型的workbook
     *
     * @param version
     * @param workbook
     * @param sheetName
     * @param condition
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Workbook buildSheetOfArray(ExcelVersion version, Workbook workbook, String sheetName, ExportExcelCondition<T> condition) throws IllegalAccessException  {

        if (workbook == null) {
            throw new NullPointerException("Workbook is null");
        }

        if (condition == null) {
            throw new NullPointerException("ExportExcelCondition is null");
        }

        if (condition.getType() != ExportExcelCondition.ARRAY) {
            throw new IllegalArgumentException("only support ExportExcelCondition.ARRAY");
        }

        //创建一个sheet表
        Sheet sheet = newSheetInstance(workbook, sheetName);
        //使用默认sheet样式
        setSheetDefaultOptions(sheet);
        //使用默认头部样式
        CellStyle headStyle = setHeadCellStyleDefaultOptions(newCellStyleInstance(workbook));
        //使用默认头部字体
        Font headFont = setHeadFontDefaultOptions(newFontInstance(workbook));
        setCellStyleFont(headStyle, headFont);
        //使用默认数据行样式
        CellStyle dataStyle = setDataCellStyleDefaultOptions(newCellStyleInstance(workbook));
        //使用默认数据行字体
        Font dataFont = setDataFontDefaultOptions(newFontInstance(workbook));
        setCellStyleFont(dataStyle, dataFont);

        //生成表头
        String[] headers = condition.getHeaders();
        if (headers != null && headers.length > 0) {
            Row row = newRowInstance(sheet, 0);
            for (int i = 0; i < headers.length; i++) {
                setRowCellStyle(newCellInstance(row, i), headStyle).setCellValue(newRichTextStringInstance(version, headers[i]));
            }
        }
        //生成数据行
        Collection<T> data = condition.getData();
        FieldControlObject[] fieldControlObjects = condition.getFieldControlObjects();
        if (data != null) {
            int rowNum = 1;
            //遍历数据行
            for (T t : data) {
                Row row = newRowInstance(sheet, rowNum);
                if (t instanceof Object[]) {
                    Object[] fields = (Object[]) t;
                    //遍历第二维对象数组
                    int colNum = 0;

                    for (FieldControlObject fieldControlObject : fieldControlObjects) {
                        //设置数据行样式
                        Cell cell = newCellInstance(row, colNum);
                        cell.setCellStyle(dataStyle);
                        //获取值
                        Object value = fields[colNum];
                        //获取值导出类型
                        ExportExcelCellType fieldType = fieldControlObject.getFieldType();
                        //根据fieldType处理对象数组类型数据
                        excelArrayHandlerByFieldType(version, workbook, sheet, row, colNum, rowNum, fieldControlObject, cell, value, fieldType);
                        //下一列
                        colNum++;
                    }

                } else {
                    throw new IllegalArgumentException("need Object[]");
                }
                rowNum++;
            }
        }
        return workbook;
    }

    /**
     * 根据fieldType处理二维数组类型数据
     *
     * @param workbook
     * @param sheet
     * @param row
     * @param colNum
     * @param rowNum
     * @param fieldControlObject
     * @param cell
     * @param value
     * @param fieldType
     */
    private static void excelArrayHandlerByFieldType(ExcelVersion version, Workbook workbook, Sheet sheet, Row row, int colNum, int rowNum, FieldControlObject fieldControlObject, Cell cell, Object value, ExportExcelCellType fieldType) {
        switch (fieldType) {
            case RICH_TEXT_STRING:
                cell.setCellValue(newRichTextStringInstance(version, String.valueOf(value)));
                break;
            case DATE:
                excelDateFieldTypeHandler(fieldControlObject, cell, value);
                break;
            case LINK_URL:
                excelLinkUrlFieldTypeHandler(version, workbook, fieldControlObject, cell, value);
                break;
            case IMG_BYTE:
                excelImageByteFieldTypeHandler(workbook, sheet, row, colNum, rowNum, value);
                break;
            case IMG_URL:
                excelImgUrlFieldTypeHandler(workbook, sheet, row, colNum, rowNum, fieldControlObject, value);
                break;
            default:
                throw new UnsupportedOperationException("can not support current ExportExcelCellType");
        }
    }

    /**
     * 导出IMG_URL类型处理器
     *
     * @param workbook
     * @param sheet
     * @param row
     * @param colNum
     * @param rowNum
     * @param fieldControlObject
     * @param value
     */
    private static void excelImgUrlFieldTypeHandler(Workbook workbook, Sheet sheet, Row row, int colNum, int rowNum, FieldControlObject fieldControlObject, Object value) {
        String separatorChars;
        int index;
        String[] urls;//图片url字符串类型
        if (value instanceof String) {
            //设置图片单元格宽高
            sheet.setColumnWidth(colNum, ExportExcelCondition.getImgColumnWidth());
            row.setHeight(ExportExcelCondition.getImgRowHeight());
            //根据分隔符截取
            separatorChars = fieldControlObject.getSeparatorChars();
            index = fieldControlObject.getIndex();
            urls = StringUtils.split(String.valueOf(value), separatorChars);
            if (index < urls.length) {
                //获取url对应byte数组
                byte[] imgByte = getImgFromQiniu(urls[index]);
                if (imgByte != null && imgByte.length > 0) {
                    setPicOfCell(workbook, sheet, colNum, rowNum, imgByte);
                }
            }
        } else {
            throw new UnsupportedOperationException("field can not match ExportExcelCellType.IMG_URL");
        }
    }

    /**
     * 导出IMG_BYTE类型处理器
     *
     * @param workbook
     * @param sheet
     * @param row
     * @param colNum
     * @param rowNum
     * @param value
     */
    private static void excelImageByteFieldTypeHandler(Workbook workbook, Sheet sheet, Row row, int colNum, int rowNum, Object value) {
        //图片字节数组类型
        if (value instanceof byte[]) {
            //设置图片单元格宽高
            sheet.setColumnWidth(colNum, ExportExcelCondition.getImgColumnWidth());
            row.setHeight(ExportExcelCondition.getImgRowHeight());
            setPicOfCell(workbook, sheet, colNum, rowNum, (byte[]) value);
        } else {
            throw new UnsupportedOperationException("field can not match ExportExcelCellType.IMG_BYTE");
        }
    }

    /**
     * 导出LINK_URL类型处理器
     *
     * @param fieldControlObject
     * @param cell
     * @param value
     */
    private static void excelLinkUrlFieldTypeHandler(ExcelVersion version, Workbook workbook, FieldControlObject fieldControlObject, Cell cell, Object value) {
        String separatorChars;
        int index;
        String[] urls;
        if (value instanceof String) {
            //url类型
            separatorChars = fieldControlObject.getSeparatorChars();
            index = fieldControlObject.getIndex();
            urls = StringUtils.split(String.valueOf(value), separatorChars);
            Hyperlink link = newHyperlinkInstance(version, workbook, Hyperlink.LINK_URL);
            if (index < urls.length) {
                cell.setCellValue(urls[index]);
                link.setAddress(urls[index]);
                cell.setHyperlink(link);
            }
        } else {
            throw new UnsupportedOperationException("field can not match ExportExcelCellType.LINK_URL");
        }
    }

    /**
     * 导出日期格式处理器
     *
     * @param fieldControlObject
     * @param cell
     * @param value
     */
    private static void excelDateFieldTypeHandler(FieldControlObject fieldControlObject, Cell cell, Object value) {
        String pattern;
        if (value instanceof Date) {
            //日期类型，获取格式化的pattern
            pattern = fieldControlObject.getPattern();
            cell.setCellValue(new SimpleDateFormat(pattern).format(value));
        } else {
            throw new UnsupportedOperationException("field can not match ExportExcelCellType.DATE");
        }
    }

    /**
     * 设置单元格为图片
     *
     * @param workbook
     * @param sheet
     * @param colNum
     * @param rowNum
     * @param value
     */
    private static void setPicOfCell(Workbook workbook, Sheet sheet, int colNum, int rowNum, byte[] value) {
        int pictureIdx = workbook.addPicture(value, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片插入坐标
        anchor.setCol1(colNum);
        anchor.setRow1(rowNum);
        // 插入图片
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize();
    }

    /**
     * 根据字段名获取对应Field对象
     *
     * @param fields
     * @param fieldName
     * @return
     */
    public static Field getFieldByName(Field[] fields, String fieldName) {
        for (Field field : fields) {
            //包含
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    /**
     * 从七牛下载图片
     *
     * @param url
     * @return
     */
    private static byte[] getImgFromQiniu(String url) {
        //指定宽高缩放
        url += "?imageMogr2/thumbnail/" + ExportExcelCondition.getImgWidth() + "x" + ExportExcelCondition.getImgHeight() + "!";
        return FileUtils.getByteFromNetByUrl(url);
    }

    /**
     * 导出excel
     *
     * @param version
     * @param workbook
     * @param resp
     */
    public static void export(ExcelVersion version, Workbook workbook, HttpServletResponse resp) {
        //创建excel文件名
        DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = timeFormat.format(new Date()) + getExtName(version);
        try (OutputStream out = resp.getOutputStream()) {
            //设置返回头
            resp.setContentType("application/ms-excel;charset=UTF-8");
            resp.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            //写入流
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据版本获取导出excel后缀名
     *
     * @param version
     * @return
     */
    private static String getExtName(ExcelVersion version) {
        switch (version) {
            case VERSION_2003:
                return ".xls";
            case VERSION_2007:
                return ".xlsx";
            default:
                return ".xlsx";
        }
    }

    /**
     * 只导出一张sheet表
     *
     * @param sheetName
     * @param condition
     * @param resp
     * @throws IllegalAccessException
     */
    public static <T> void exportOneSheet(String sheetName, ExportExcelCondition<T> condition, HttpServletResponse resp) throws IllegalAccessException {
        //获取导出版本
        ExcelVersion version = condition.getVersion();
        //创建一个工作簿
        Workbook workbook = newWorkbookInstance(version);
        if (ExportExcelCondition.BEAN == condition.getType()) {
            //写入工作簿中
            buildSheetOfBean(version, workbook, sheetName, condition);
            //导出
            export(version, workbook, resp);
        } else if (ExportExcelCondition.ARRAY == condition.getType()) {
            //写入工作簿中
            buildSheetOfArray(version, workbook, sheetName, condition);
            //导出
            export(version, workbook, resp);
        } else {
            throw new IllegalArgumentException("ExportExcelCondition's ExportExcelDataType is required");
        }
    }

}
