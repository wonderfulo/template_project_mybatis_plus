package com.cxy.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @author 陈翔宇
 * @since 2020-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@TableName(value = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
    @TableId(value = "id",type = IdType.AUTO)//指定自增策略
    private Integer id;

    /**
     * 姓名
     *
     * 若没有开启驼峰命名，或者表中列名不符合驼峰规则，可通过该注解指定数据库表中的列名，exist标明数据表中有没有对应列
     */
//    @TableField(value = "user_name",exist = true)
    private String userName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 记录
     */
    private String note;

    /**
     * 邮箱
     */
    private String email;


}
