package com.csun.mall.pojo.dto;

import com.csun.mall.domain.OrderItems;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/11/3 10:53
 */
@Data
public class OrdersDTO {
    public Long id;
    public BigDecimal totalAmount;
    public Date createTime;
    public List<OrderItems> orderItems;
}
