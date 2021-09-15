package com.csun.mall.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Joker Zheng
 * @create 2021/9/3 16:44
 */
@Data
public class ProductQueryDTO {
    @ApiModelProperty("商品id")
    private Long id;
    @ApiModelProperty("商品是否启用")
    private Boolean enable;
    @ApiModelProperty("商品分类")
    private Long productCategoryId;
    @ApiModelProperty("商品分类名称")
    private String categoryName;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("销量")
    private Integer sale;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("市场价")
    private BigDecimal originalPrice;
    @ApiModelProperty("库存")
    private Integer stock;
    @ApiModelProperty("库存预警值")
    private Integer lowStock;
    @ApiModelProperty("商品描述")
    private String description;
    @ApiModelProperty("富文本")
    private String detailDesc;
    @ApiModelProperty("主图")
    private String pic;

}
