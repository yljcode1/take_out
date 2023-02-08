package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.common.Response;
import com.yao.dto.DishDTO;
import com.yao.entity.Dish;
import com.yao.entity.DishFlavor;
import com.yao.mapper.DishFlavorMapper;
import com.yao.service.DishFlavorService;
import com.yao.service.DishService;
import com.yao.mapper.DishMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yao
 * @description 针对表【dish(菜品)】的数据库操作Service实现
 * @createDate 2023-02-01 19:22:19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {
    private final DishMapper dishMapper;
    private final DishFlavorService dishFlavorMapper;

    /**
     * 菜品列表
     *
     * @param page     页码
     * @param pageSize 页面大小
     * @param name     模糊搜索名称
     * @return 菜品列表
     */
    @Override
    public Response<IPage<Dish>> page(Integer page, Integer pageSize, String name) {
        IPage<Dish> dishIPage = new Page<>(page, pageSize);
        IPage<Dish> dishPage = dishMapper.selectPage(dishIPage, new LambdaQueryWrapper<Dish>().like(StringUtils.isNotBlank(name), Dish::getName, name).orderByDesc(Dish::getUpdateTime));
        return Response.success(dishPage);
    }

    @Override
    @Transactional
    public void saveFlavor(DishDTO dishDTO) {
        this.save(dishDTO);
        Long dishId = dishDTO.getId();
        List<DishFlavor> dishFlavorsList = dishDTO.getDishFlavorsList();
        dishFlavorsList = dishFlavorsList.stream().peek((item) -> item.setDishId(dishId)).collect(Collectors.toList());
        // 保存菜品口味数据到菜品口味表
        dishFlavorMapper.saveBatch(dishFlavorsList);
    }
}




