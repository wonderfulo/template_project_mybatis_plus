package com.cxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改者ID
     */
    private Long updateUserId;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0: 可用, 1: 删除
     */
    private Integer isDelete;

}
