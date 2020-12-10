package com.cxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 系统用户Id
     */
    @TableId(value = "sys_user_id", type = IdType.AUTO)
    private Long sysUserId;

    /**
     * 系统用户编码
     */
    private String userCode;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    private String name;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 用户状态。1：注册，2：激活，3：离职申请，4：离职，5：入店批准，6：在职（正常员工状态信息）
     */
    private String userStauts;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ
     */
    private Long qq;

    /**
     * 微信
     */
    private String wx;

    /**
     * 头像
     */
    private String headPic;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 0或空是第一次登陆，1不是第一次登陆
     */
    private String checkFirstLogin;

    /**
     * 企业id
     */
    private String appId;


}
