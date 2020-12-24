package com.cxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxy.entity.Nation;
import com.cxy.mapper.NationMapper;
import com.cxy.service.INationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.Na;
import org.apache.poi.ss.formula.functions.T;
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
     * @param id 主键ID
     */
    @Override
    public Nation getExistById(Serializable id) {
        QueryWrapper<Nation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nation_id", id);
        queryWrapper.eq("is_delete", 0);
        baseMapper.selectOne(queryWrapper);
        return baseMapper.selectOne(queryWrapper);
    }
}
