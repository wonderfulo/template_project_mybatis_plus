package com.cxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entity.Nation;

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
    Nation getByIdAndIsDelete(Serializable id);

    /**
     * 根据 ID 查询 未被删除的对象
     * 参数查询，分页获取
     */
    IPage<Nation> getList(Nation nation , Page<Nation> page);

}
