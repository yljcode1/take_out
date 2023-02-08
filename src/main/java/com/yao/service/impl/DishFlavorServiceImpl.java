package com.yao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.entity.DishFlavor;
import com.yao.service.DishFlavorService;
import com.yao.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author yao
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-02-08 20:14:27
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




