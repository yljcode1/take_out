package com.yao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yao.common.Response;
import com.yao.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author yao
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-01-31 22:17:09
*/
public interface CategoryService extends IService<Category> {
    /**
     * 获取菜单分页
     *
     * @param page     页码
     * @param pageSize 页面大小
     * @return 菜单分页
     */
    Response<IPage<Category>> findAll(Integer page, Integer pageSize);

    void delete(Long id);
    /**
     * 修改分类
     *
     * @param category 分类
     * @return 修改结果
     */
    void update(Category category);

    /**
     * 获取菜单分页
     *
     * @return 菜单分页
     */
    Response<List<Category>> listDict();
}
