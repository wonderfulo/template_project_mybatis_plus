package com.cxy.utils.excel2;

/**
 * 导出excel单元格类型
 *
 * @author wangpeng
 * @date 2018/6/25
 */
public enum ExportExcelCellType {

    /**
     * 字符串类型
     */
    STRING,
    /**
     * 富文本字符串类型
     */
    RICH_TEXT_STRING,
    /**
     * Date日期类型
     */
    DATE,
    /**
     * 图片字节数组类型
     */
    IMG_BYTE,
    /**
     * 图片url字符串类型
     */
    IMG_URL,
    /**
     * link的url类型
     */
    LINK_URL,
    /**
     * link的document类型
     */
    LINK_DOCUMENT,
    /**
     * link的email类型
     */
    LINK_EMAIL,
    /**
     * link的file类型
     */
    LINK_FILE;

    /**
     * 构建全富文本的ExportExcelCellType
     *
     * @param length
     * @return
     */
    public static ExportExcelCellType[] buildAllRichTextStringType(int length) {
        ExportExcelCellType[] types = new ExportExcelCellType[length];
        for (int i = 0; i < length; i++) {
            types[i] = RICH_TEXT_STRING;
        }
        return types;
    }
}
