package com.csun.mall.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Orders {
    /**
     * 订单主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 订单总价格
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 创建时间（成交时间）
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取订单主键
     *
     * @return id - 订单主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置订单主键
     *
     * @param id 订单主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return member_id - 用户id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置用户id
     *
     * @param memberId 用户id
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取订单总价格
     *
     * @return total_amount - 订单总价格
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置订单总价格
     *
     * @param totalAmount 订单总价格
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取创建时间（成交时间）
     *
     * @return created_time - 创建时间（成交时间）
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间（成交时间）
     *
     * @param createdTime 创建时间（成交时间）
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取更新时间
     *
     * @return updated_time - 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置更新时间
     *
     * @param updatedTime 更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}