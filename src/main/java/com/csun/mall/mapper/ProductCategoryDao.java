package com.csun.mall.mapper;

import com.csun.mall.pojo.dto.ProductCategoryWithChildren;

import java.util.List;


public interface ProductCategoryDao {
    /**
     * 获取商品分类及其子分类
     */
    List<ProductCategoryWithChildren> listWithChildren();
}
