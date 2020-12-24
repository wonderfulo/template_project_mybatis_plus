package com.cxy.controller;


import com.cxy.common.JsonResponse;
import com.cxy.entity.Nation;
import com.cxy.entity.User;
import com.cxy.service.INationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("民族控制器")
public class NationController {

    @Autowired
    private INationService iNationService;

    @GetMapping("/{nationId}")
    @ApiOperation(
            value = "获取测试用户列表",
            notes = "创建时间倒序",
            httpMethod = "GET",
            response = Nation.class,
            responseContainer = "Object")
    public JsonResponse<Nation> get(String accessToken, @PathVariable("nationId") Long nationId) {
        Nation nation = iNationService.getExistById(nationId);
        return JsonResponse.success(nation);
    }
}
