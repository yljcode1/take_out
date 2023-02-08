package com.yao.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yao.common.Response;
import com.yao.dto.DishDTO;
import com.yao.entity.Dish;
import com.yao.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品控制层
 *
 * @date: 2023-02-01
 * @author: yao
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;

    /**
     * 菜品列表
     *
     * @param page     页码
     * @param pageSize 页面大小
     * @param name     模糊搜索名称
     * @return 菜品列表
     */
    @GetMapping("/page")
    public Response<IPage<Dish>> page(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, String name) {
        return dishService.page(page, pageSize, name);
    }

    /**
     * 添加菜品
     *
     * @param dishDTO DTO
     * @return 添加结果
     */
    @PostMapping
    public Response<String> save(@RequestBody DishDTO dishDTO) {
        // 操作两张表
        dishService.saveFlavor(dishDTO);
        return null;
    }
}
