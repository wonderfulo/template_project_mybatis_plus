package com.cxy.service.impl;

import com.cxy.entity.TUser;
import com.cxy.mapper.TUserMapper;
import com.cxy.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-11-19
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
