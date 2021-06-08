package com.cxy.service.impl;

import com.cxy.entity.TmSysUser;
import com.cxy.mapper.SysUserMapper;
import com.cxy.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, TmSysUser> implements ISysUserService {

}
