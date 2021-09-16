package com.csun.mall.mapper;

import com.csun.mall.pojo.dto.ProductCategoryWithChildren;

import java.util.List;

/**
 * 商品分类自定义Dao
 * Created by macro on 2018/5/25.
 */
public interface ProductCategoryDao {
    /**
     * 获取商品分类及其子分类
     */
    List<ProductCategoryWithChildren> listWithChildren();
}
