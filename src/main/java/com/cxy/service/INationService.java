package com.cxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.entity.Nation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 民族表 服务类
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
public interface INationService extends IService<Nation> {

    /**
     * 根据 ID 查询 未被删除的对象
     * @param id 主键ID
     */
    Nation getExistById(Serializable id);

}
