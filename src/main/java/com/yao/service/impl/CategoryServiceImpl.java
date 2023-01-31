package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.common.Response;
import com.yao.entity.Category;
import com.yao.service.CategoryService;
import com.yao.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yao
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2023-01-31 22:17:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
    private final CategoryMapper categoryMapper;

    @Override
    public Response<IPage<Category>> findAll(Integer page, Integer pageSize) {
        IPage<Category> categoryPage = new Page<>(page, pageSize);
        IPage<Category> categoryIPage = categoryMapper.selectPage(categoryPage, new LambdaQueryWrapper<Category>().orderByAsc(Category::getSort));
        return Response.success(categoryIPage);
    }
}




