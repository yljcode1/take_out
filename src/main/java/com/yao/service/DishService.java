package com.yao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yao.common.Response;
import com.yao.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yao
* @description 针对表【dish(菜品)】的数据库操作Service
* @createDate 2023-02-01 19:22:19
*/
public interface DishService extends IService<Dish> {
    /**
     * 菜品列表
     *
     * @param page     页码
     * @param pageSize 页面大小
     * @param name     模糊搜索名称
     * @return 菜品列表
     */
    Response<IPage<Dish>> page(Integer page, Integer pageSize, String name);
}
