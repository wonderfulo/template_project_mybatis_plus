//package com.cxy.storeinsp;
//
//import com.alibaba.excel.metadata.CellData;
//import com.alibaba.excel.metadata.Head;
//import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
//import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
//import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.common.usermodel.HyperlinkType;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CreationHelper;
//import org.apache.poi.ss.usermodel.Hyperlink;
//
//import java.util.List;
//
//public class CustomCellWriteHandler extends AbstractCellWriteHandler {
//
//
//    @Override
//    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
//                                 List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
//        // 非首行,单元格不为空
//        if (!isHead && StringUtils.isNotBlank(cell.getStringCellValue()) && cell.getColumnIndex() >= 19 && cell.getColumnIndex() <= 28) {
//            CreationHelper createHelper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
//            Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.URL);
//            hyperlink.setAddress(cellDataList.get(0).getStringValue());
//            cell.setHyperlink(hyperlink);
//            System.out.println(cellDataList);
//        }
//    }
//
//}