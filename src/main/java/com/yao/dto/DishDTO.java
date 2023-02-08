package com.yao.dto;

import com.yao.entity.Dish;
import com.yao.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装Dish的DTO
 *
 * @date: 2023-02-08
 * @author: yao
 */
@Data
public class DishDTO extends Dish {
    private List<DishFlavor> dishFlavorsList = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
