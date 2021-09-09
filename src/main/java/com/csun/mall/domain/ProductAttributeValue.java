package com.csun.mall.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product_attribute_value")
public class ProductAttributeValue {
    @Id
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "product_id")
    @ApiModelProperty(hidden = true)
    private Long productId;

    @Column(name = "product_attribute_id")
    private Long productAttributeId;

    /**
     * 手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
     */
    private String value;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return product_id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return product_attribute_id
     */
    public Long getProductAttributeId() {
        return productAttributeId;
    }

    /**
     * @param productAttributeId
     */
    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    /**
     * 获取手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
     *
     * @return value - 手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
     *
     * @param value 手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
     */
    public void setValue(String value) {
        this.value = value;
    }
}