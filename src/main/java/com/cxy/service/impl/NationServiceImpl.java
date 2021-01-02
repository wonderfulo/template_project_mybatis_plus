package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.annonation.MyCache;
import com.cxy.entity.Nation;
import com.cxy.mapper.NationMapper;
import com.cxy.service.INationService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 民族表 服务实现类
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

    /**
     * 根据 ID 查询 未被删除的对象
     *
     * @param id 主键ID
     */
    @Override
    public Nation getByIdAndIsDelete(Serializable id) {
        QueryWrapper<Nation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nation_id", id);
        queryWrapper.eq("is_delete", 0);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Nation> getList(Nation nation, Page<Nation> page) {

        QueryWrapper<Nation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);

        //第一种
        IPage<Nation> iPage = baseMapper.selectPage(page, queryWrapper);
        System.out.println("总页数:" + iPage.getPages());
        System.out.println("总记录数:" + iPage.getTotal());

        return iPage;
    }

    @Override
    @MyCache(key = "nation:get:id")
    public Nation get(Serializable id) {
        QueryWrapper<Nation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nation_id", id);
        queryWrapper.eq("is_delete", 0);
        Nation nation = baseMapper.selectOne(queryWrapper);
        return nation;
    }
}
