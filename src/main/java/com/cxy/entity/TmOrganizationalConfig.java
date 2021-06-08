package com.cxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 组织结构配置
 * </p>
 *
 * @author 陈翔宇
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TmOrganizationalConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 企业Id
     */
    private String appId;


}
