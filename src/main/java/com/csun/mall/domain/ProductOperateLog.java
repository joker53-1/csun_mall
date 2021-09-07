package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "product_operate_log")
public class ProductOperateLog {
    @Id
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "price_old")
    private BigDecimal priceOld;

    @Column(name = "price_new")
    private BigDecimal priceNew;

    /**
     * 操作人
     */
    @Column(name = "operate_man")
    private String operateMan;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return price_old
     */
    public BigDecimal getPriceOld() {
        return priceOld;
    }

    /**
     * @param priceOld
     */
    public void setPriceOld(BigDecimal priceOld) {
        this.priceOld = priceOld;
    }

    /**
     * @return price_new
     */
    public BigDecimal getPriceNew() {
        return priceNew;
    }

    /**
     * @param priceNew
     */
    public void setPriceNew(BigDecimal priceNew) {
        this.priceNew = priceNew;
    }

    /**
     * 获取操作人
     *
     * @return operate_man - 操作人
     */
    public String getOperateMan() {
        return operateMan;
    }

    /**
     * 设置操作人
     *
     * @param operateMan 操作人
     */
    public void setOperateMan(String operateMan) {
        this.operateMan = operateMan;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}