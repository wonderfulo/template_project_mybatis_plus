package com.cxy.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.common.JsonResponse;
import com.cxy.entity.Nation;
import com.cxy.entity.SysUser;
import com.cxy.entity.User;
import com.cxy.service.INationService;
import com.cxy.utils.request.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 民族表 前端控制器
 * </p>
 *
 * @author 陈翔宇
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/nation")
@Api(value = "民族API接口",
        description = "民族API接口")
public class NationController {

    @Autowired
    private INationService nationService;

    @PostMapping("")
    @ApiOperation("添加民族")
    @Transactional
    public JsonResponse<Nation> add(@RequestParam(value = "accessToken", required = true) String accessToken, Nation nation) {
        if (StringUtils.isEmpty(nation.getNationName())) {
            return JsonResponse.fail("nationName: 必要参数");
        }

        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(123L);
        nation.addBuild(sysUser);

        boolean save = nationService.save(nation);
        if (save) {
            return JsonResponse.success(nation);
        } else {
            return JsonResponse.fail("添加失败");
        }
    }


    @DeleteMapping("/{nationId}")
    @ApiOperation("删除民族")
    @Transactional
    public JsonResponse<String> delete(@RequestParam(value = "accessToken", required = true) String accessToken, @PathVariable("nationId") Long nationId) {
        if (nationId == null) {
            return JsonResponse.fail("nationId: 必要参数");
        }

        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(456L);

        Nation nation = new Nation();
        nation.setNationId(nationId);
        nation.delBuild(sysUser);

        boolean update = nationService.updateById(nation);

        if (update) {
            //不返回当前对象，因为当前更新对象为差量更新（属性并不全面），先查询在返回太浪费性能
            return JsonResponse.success("删除成功");
        } else {
            return JsonResponse.fail("删除失败");
        }
    }

    /**
     * 用户测试使用map，接收json数据
     * 也可以使用 list<对象> xx， 接收json对象数组
     * @param accessToken
     * @param map
     * @return
     */
    @PostMapping("/deleteBatch")
    @ApiOperation("删除民族")
    @Transactional
    public JsonResponse<String> deleteBatch(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestBody Map map) {

//        if (nationId == null) {
//            return JsonResponse.fail("nationId: 必要参数");
//        }
//
//        SysUser sysUser = new SysUser();
//        sysUser.setSysUserId(456L);
//
//        Nation nation = new Nation();
//        nation.setNationId(nationId);
//        nation.delBuild(sysUser);
//
//        boolean update = nationService.updateById(nation);
//
//        if (update) {
//            //不返回当前对象，因为当前更新对象为差量更新（属性并不全面），先查询在返回太浪费性能
//            return JsonResponse.success("删除成功");
//        } else {
//            return JsonResponse.fail("删除失败");
//        }

        return null;
    }

    @PutMapping("")
    @ApiOperation("修改民族")
    @Transactional
    public JsonResponse<String> update(@RequestParam(value = "accessToken", required = true) String accessToken, Nation nation) {
        if (nation.getNationId() == null) {
            return JsonResponse.fail("nationId: 必要参数");
        }
        //由于不允许更新已删除的数据，故要先进行查询
        Nation oldNation = nationService.getByIdAndIsDelete(nation.getNationId());

        if (oldNation == null) {
            return JsonResponse.fail("待更新数据不存在");
        }

        BeanUtil.copyProperties(nation, oldNation, CopyOptions.create().setIgnoreNullValue(true));

        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(888L);

        oldNation.updateBuild(sysUser);

        boolean update = nationService.updateById(oldNation);

        if (update) {
            //不返回当前对象，因为当前更新对象为差量更新（属性并不全面），先查询在返回太浪费性能
            return JsonResponse.success("更新成功");
        } else {
            return JsonResponse.fail("更新失败");
        }
    }


    @GetMapping("/{nationId}")
    @ApiOperation(
            value = "获取民族对象",
            notes = "未被删除的",
            httpMethod = "GET",
            response = Nation.class,
            responseContainer = "Object")
    public JsonResponse<Nation> get(@RequestParam(value = "accessToken", required = true) String accessToken, @PathVariable("nationId") Long nationId) {
        if (nationId == null) {
            return JsonResponse.fail("nationId: 必要参数");
        }
        Nation nation = nationService.getByIdAndIsDelete(nationId);
        return JsonResponse.success(nation);
    }

    @GetMapping("/list")
    @ApiOperation(
            value = "获取民族对象列表",
            notes = "参数查询，分页获取",
            httpMethod = "GET",
            response = Nation.class,
            responseContainer = "Object")
    public JsonResponse<IPage<Nation>> list(@RequestParam(value = "accessToken", required = true) String accessToken, Nation nation, Page<Nation> page) {
        if (page.getCurrent() < 1) {
            page.setCurrent(1);
        }

        IPage<Nation> list = nationService.getList(nation, page);
        return JsonResponse.success(list);
    }


    @GetMapping("/myCache/{nationId}")
    @ApiOperation(
            value = "获取民族对象",
            notes = "未被删除的",
            httpMethod = "GET",
            response = Nation.class,
            responseContainer = "Object")
    public JsonResponse<Nation> getOnMyCache(@RequestParam(value = "accessToken", required = true) String accessToken, @PathVariable("nationId") Long nationId) {
        if (nationId == null) {
            return JsonResponse.fail("nationId: 必要参数");
        }
        Nation nation = nationService.get(nationId);
        return JsonResponse.success(nation);
    }
}
