package com.csun.mall.domain;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "order_items")
public class OrderItems {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 归属订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 商品图片
     */
    @Column(name = "item_img")
    private String itemImg;

    /**
     * 商品名称
     */
    @Column(name = "item_name")
    private String itemName;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取归属订单id
     *
     * @return order_id - 归属订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置归属订单id
     *
     * @param orderId 归属订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取商品图片
     *
     * @return item_img - 商品图片
     */
    public String getItemImg() {
        return itemImg;
    }

    /**
     * 设置商品图片
     *
     * @param itemImg 商品图片
     */
    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    /**
     * 获取商品名称
     *
     * @return item_name - 商品名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置商品名称
     *
     * @param itemName 商品名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取成交价格
     *
     * @return price - 成交价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置成交价格
     *
     * @param price 成交价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取购买数量
     *
     * @return quantity - 购买数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置购买数量
     *
     * @param quantity 购买数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}