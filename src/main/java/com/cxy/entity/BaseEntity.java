package com.cxy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


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
     * * @JsonFormat: 返回数据时，格式转换
     * * @DateTimeFormat: 请求数据时，格式转换
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改者ID
     */
    private Long updateUser;

    /**
     * 修改时间
     * * @JsonFormat: 返回数据时，格式转换
     * * @DateTimeFormat: 请求数据时，格式转换
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 对象公用参数基础构建
     *
     * @param tmSysUser
     */
    public void baseBuild(TmSysUser tmSysUser) {
        if (this.getCreateUser() == null) {
            baseCreateBuild(tmSysUser);
        }
        baseUpdateBuild(tmSysUser);
    }

    /**
     * 对象公用参数基础构建
     * 创建信息
     *
     * @param tmSysUser
     */
    public void baseCreateBuild(TmSysUser tmSysUser) {
        this.setCreateUser(tmSysUser.getSysUserId());
        this.setCreateTime(new Date());
    }

    /**
     * 对象公用参数基础构建
     * 更新信息
     *
     * @param tmSysUser
     */
    public void baseUpdateBuild(TmSysUser tmSysUser) {
        this.setUpdateUser(tmSysUser.getSysUserId());
        this.setUpdateTime(new Date());
    }

    /**
     * 对象公用参数基础构建
     * 添加对象时使用
     *
     * @param tmSysUser
     */
    public void addBuild(TmSysUser tmSysUser) {
        this.setIsDelete(0);
        baseBuild(tmSysUser);
    }

    /**
     * 对象公用参数基础构建
     * 删除对象时使用
     *
     * @param tmSysUser
     */
    public void delBuild(TmSysUser tmSysUser) {
        this.setIsDelete(1);
        baseUpdateBuild(tmSysUser);
    }

    /**
     * 对象公用参数基础构建
     * 更新对象时使用
     *
     * @param tmSysUser
     */
    public void updateBuild(TmSysUser tmSysUser) {
        baseUpdateBuild(tmSysUser);
    }

}
