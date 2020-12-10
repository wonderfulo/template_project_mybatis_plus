package com.cxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class Nation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "nation_id", type = IdType.AUTO)
    private Long nationId;

    private String nation;


}
