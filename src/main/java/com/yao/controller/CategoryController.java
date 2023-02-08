package com.yao.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.regexp.internal.RE;
import com.yao.common.BaseException;
import com.yao.common.Response;
import com.yao.entity.Category;
import com.yao.entity.Dish;
import com.yao.mapper.DishMapper;
import com.yao.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 获取分类字典表
     *
     * @return 菜单分页
     */
    @GetMapping("/list")
    public Response<List<Category>> list() {
        return categoryService.listDict();
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

    /**
     * 删除分类
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping
    public Response<String> delete(@RequestParam Long id) {
        categoryService.delete(id);
        return Response.success("删除成功");
    }

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 修改结果
     */
    @PutMapping
    public Response<String> update(@RequestBody Category category) {
        categoryService.update(category);
        return Response.success("修改成功");
    }
}
