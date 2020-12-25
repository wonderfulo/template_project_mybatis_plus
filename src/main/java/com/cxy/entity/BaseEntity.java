package com.cxy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
     *  * @JsonFormat: 返回数据时，格式转换
     *  * @DateTimeFormat: 请求数据时，格式转换
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改者ID
     */
    private Long updateUser;

    /**
     * 修改时间
     *  * @JsonFormat: 返回数据时，格式转换
     *  * @DateTimeFormat: 请求数据时，格式转换
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 对象公用参数基础构建
     * @param sysUser
     */
    public void baseBuild(SysUser sysUser){
        if (this.getCreateUser() == null){
            this.setCreateUser(sysUser.getSysUserId());
            this.setCreateTime(new Date());
        }
        this.setUpdateUser(sysUser.getSysUserId());
        this.setUpdateTime(new Date());
    }

    /**
     * 对象公用参数基础构建
     * 添加对象时使用
     * @param sysUser
     */
    public void addBuild(SysUser sysUser){
        this.setIsDelete(0);
        baseBuild(sysUser);
    }

}
