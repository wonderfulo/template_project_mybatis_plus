package com.cxy.excel_ali.test.temp.dataformat;

import com.alibaba.excel.metadata.CellData;

import lombok.Data;

/**
 * TODO
 *
 * @author 罗成
 **/
@Data
public class DataFormatData {
    private CellData<String> date;
    private CellData<String> num;
}
