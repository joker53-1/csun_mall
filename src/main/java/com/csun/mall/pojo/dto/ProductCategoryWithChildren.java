package com.csun.mall.pojo.dto;

import com.csun.mall.domain.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/15 16:03
 */
public class ProductCategoryWithChildren extends ProductCategory {
    @Getter
    @Setter
    @ApiModelProperty("子级分类")
    private List<ProductCategory> children;
}
