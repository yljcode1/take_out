package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.common.BaseException;
import com.yao.common.Response;
import com.yao.entity.Category;
import com.yao.entity.Dish;
import com.yao.entity.Setmeal;
import com.yao.mapper.DishMapper;
import com.yao.mapper.SetmealMapper;
import com.yao.service.CategoryService;
import com.yao.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    @Override
    public Response<IPage<Category>> findAll(Integer page, Integer pageSize) {
        IPage<Category> categoryPage = new Page<>(page, pageSize);
        IPage<Category> categoryIPage = categoryMapper.selectPage(categoryPage, new LambdaQueryWrapper<Category>().orderByAsc(Category::getSort));
        return Response.success(categoryIPage);
    }

    @Override
    public void delete(Long id) {
        // 查看关联的菜品
        if (dishMapper.selectCount(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, id)) > 0 || setmealMapper.selectCount(new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getCategoryId, id)) > 0) {
            // 该菜品下面有关联菜，不允许删除
            throw new BaseException("该分类下存在菜品，删除失败");
        }
        categoryMapper.delete(new LambdaQueryWrapper<Category>().eq(Category::getId, id));
    }

    @Override
    public void update(Category category) {
        log.info("修改菜品分类");
        categoryMapper.updateById(category);
    }

    @Override
    public Response<List<Category>> listDict() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<Category>().orderByAsc(Category::getSort));
        return Response.success(categories);
    }
}




