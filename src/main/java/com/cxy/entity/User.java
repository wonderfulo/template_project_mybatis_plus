package com.cxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 陈翔宇
 * @since 2020-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@TableName(value = "user")
public class User implements Cloneable,Serializable {

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


    /**
     * 邮箱
     */
    private Boolean isDelete;

    private List<String> list = new ArrayList<>();


    @Override
    protected User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }


    /**
     * 深拷贝
     * 当对象有嵌套关系时，每个嵌套的对象都需要实现深拷贝
     * @return
     */
    public User deepClone() {

        try {
            //输出 序列化
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            //输入 反序列化
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            User copyObj = (User) ois.readObject();

            return copyObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
