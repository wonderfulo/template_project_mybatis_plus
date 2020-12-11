package com.cxy.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: template_project_mybatis_plus
 * @description: 基础实体类
 * @author: 陈翔宇
 * @create_time: 2020-11-19 09:11:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity {

    /**
     * 0: 可用, 1: 删除
     */
    private Integer isDelete;

    /**
     * 创建者ID
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改者ID
     */
    private Long updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

}
