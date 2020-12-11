package com.cxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文档表
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TmDocument extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文档id
     */
    @TableId(value = "document_id", type = IdType.AUTO)
    private Long documentId;

    /**
     * 文档名称
     */
    private String documentName;

    /**
     * 文档类型，只能为：EXCEL、DOCX、PPT、PDF, 默认为PDF
     */
    private String documentType;

    /**
     * 文档大小，单位：KB
     */
    private Long documentSize;

    /**
     * 文档路径（路径来源与七牛云）
     */
    private String documentPath;

    /**
     * 企业id
     */
    private String appId;


}
