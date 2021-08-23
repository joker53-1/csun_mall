package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "csr_member_tag")
public class CsrMemberTag {
    @Id
    private Long id;

    private String name;

    /**
     * 自动打标签完成订单数量
     */
    @Column(name = "finish_order_count")
    private Integer finishOrderCount;

    /**
     * 自动打标签完成订单金额
     */
    @Column(name = "finish_order_amount")
    private BigDecimal finishOrderAmount;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取自动打标签完成订单数量
     *
     * @return finish_order_count - 自动打标签完成订单数量
     */
    public Integer getFinishOrderCount() {
        return finishOrderCount;
    }

    /**
     * 设置自动打标签完成订单数量
     *
     * @param finishOrderCount 自动打标签完成订单数量
     */
    public void setFinishOrderCount(Integer finishOrderCount) {
        this.finishOrderCount = finishOrderCount;
    }

    /**
     * 获取自动打标签完成订单金额
     *
     * @return finish_order_amount - 自动打标签完成订单金额
     */
    public BigDecimal getFinishOrderAmount() {
        return finishOrderAmount;
    }

    /**
     * 设置自动打标签完成订单金额
     *
     * @param finishOrderAmount 自动打标签完成订单金额
     */
    public void setFinishOrderAmount(BigDecimal finishOrderAmount) {
        this.finishOrderAmount = finishOrderAmount;
    }
}