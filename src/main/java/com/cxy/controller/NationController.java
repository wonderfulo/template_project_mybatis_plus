package com.cxy.controller;


import com.cxy.common.JsonResponse;
import com.cxy.entity.Nation;
import com.cxy.entity.SysUser;
import com.cxy.entity.User;
import com.cxy.service.INationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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
    private INationService iNationService;

    @PostMapping("")
    @ApiOperation("添加民族")
    @Transactional
    public JsonResponse<Nation> add(@RequestParam(value = "accessToken",required = true) String accessToken, Nation nation) {
        if (StringUtils.isEmpty(nation.getNationName())){
            return JsonResponse.fail("nationName: 必要参数");
        }

        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(123L);
        nation.addBuild(sysUser);

        boolean save = iNationService.save(nation);
        if (save){
            return JsonResponse.success(nation);
        }else{
            return JsonResponse.fail("添加失败");
        }
    }



    @GetMapping("/{nationId}")
    @ApiOperation(
            value = "获取民族对象",
            notes = "未被删除的",
            httpMethod = "GET",
            response = Nation.class,
            responseContainer = "Object")
    public JsonResponse<Nation> get(@RequestParam(value = "accessToken",required = true) String accessToken, @PathVariable("nationId") Long nationId) {
        Nation nation = iNationService.getExistById(nationId);
        return JsonResponse.success(nation);
    }
}
