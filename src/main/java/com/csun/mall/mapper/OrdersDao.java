package com.csun.mall.mapper;

import com.csun.mall.pojo.dto.OrdersDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/11/3 10:56
 */
public interface OrdersDao {
    List<OrdersDTO> getOrders(@Param("memberId")Long memberId);
}
