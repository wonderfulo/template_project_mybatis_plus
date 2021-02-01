package com.cxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entity.TmNation;

import java.io.Serializable;

/**
 * <p>
 * 民族表 服务类
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
public interface ITmNationService extends IService<TmNation> {

    /**
     * 根据 ID 查询 未被删除的对象
     * @param id 主键ID
     */
    TmNation getByIdAndIsDelete(Serializable id);

    /**
     * 根据 ID 查询 未被删除的对象
     * 参数查询，分页获取
     */
    IPage<TmNation> getList(TmNation tmNation, Page<TmNation> page);

    /**
     * 测试自定义缓存注解
     * 参数查询，分页获取
     */
    TmNation get(Serializable id);

}
