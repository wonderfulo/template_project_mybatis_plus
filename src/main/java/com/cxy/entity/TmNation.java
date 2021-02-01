package com.cxy.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 民族表
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
// chain的中文含义是链式的，设置为true，则setter方法返回当前对象
//@Accessors(chain = true), 该注解会导致 ali的 easyExcel文件解析失败
@ApiModel(value="tm_nation",description="民族实体类" )
public class TmNation extends BaseEntity {

//    private static final long serialVersionUID = 1L;

    @TableId(value = "nation_id", type = IdType.AUTO)
    @ExcelIgnore
    private Long nationId;

    @ExcelProperty("民族")
    private String nationName;

}
