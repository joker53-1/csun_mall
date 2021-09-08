package com.csun.mall.pojo.dto;

import com.csun.mall.domain.ProductAttributeValue;
import com.csun.mall.domain.ProductImg;
import com.csun.mall.domain.ProductItem;
import com.csun.mall.domain.ProductLadderPrice;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductParam{
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty("商品是否启用")
    private Boolean enable;
    @ApiModelProperty("商品分类")
    private Long productCategoryId;
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
    @ApiModelProperty("商品参数及自定义规格属性")
    private List<ProductAttributeValue> productAttributeValueList;

    @ApiModelProperty("商品图片")
    private List<ProductImg> image;

    @ApiModelProperty("商品阶梯价格")
    private List<ProductLadderPrice> prices;
}
