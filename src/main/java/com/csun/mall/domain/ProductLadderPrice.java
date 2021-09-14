package com.csun.mall.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "product_ladder_price")
public class ProductLadderPrice {
    @Id
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(name = "product_id")
    @ApiModelProperty(hidden = true)
    private Long productId;

    private Long count;

    private BigDecimal price;

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
     * @return count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}