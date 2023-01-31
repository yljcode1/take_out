package com.yao.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.regexp.internal.RE;
import com.yao.common.Response;
import com.yao.entity.Category;
import com.yao.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增分类
     *
     * @param category 菜单分类
     * @return 返回值
     */
    @PostMapping
    public Response<String> save(@RequestBody Category category) {
        log.info("添加category:{}", category.toString());
        category.setStatus(1);
        categoryService.save(category);
        return Response.success("添加成功");
    }

    @DeleteMapping
    public Response<String> delete(@RequestParam Long id) {
        categoryService.removeById(id);
        return Response.success("删除成功");
    }
}
