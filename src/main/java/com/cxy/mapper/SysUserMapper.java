package com.cxy.mapper;

import com.cxy.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<Object> getListTest();

    List<String> getConcatWsTest();


}
