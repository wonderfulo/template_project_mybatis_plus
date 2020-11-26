package com.cxy.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String pwd;

    private Integer available;

    private String note;


}
