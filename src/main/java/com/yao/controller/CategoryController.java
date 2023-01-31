package com.yao.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yao.common.Response;
import com.yao.entity.Category;
import com.yao.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单控制层
 *
 * @date: 2023-01-31
 * @author: yao
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 获取菜单分页
     *
     * @param page     页码
     * @param pageSize 页面大小
     * @return 菜单分页
     */
    @GetMapping("/page")
    public Response<IPage<Category>> findAll(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return categoryService.findAll(page, pageSize);
    }
}
