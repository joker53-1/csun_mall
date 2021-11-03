package com.csun.mall.mapper;

import com.csun.mall.pojo.dto.ShopCartDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/10/19 22:19
 */
public interface ShopCartDaoMapper {
    List<ShopCartDTO> getcartList(@Param("memberId") Long memberId);
    List<ShopCartDTO> getcartListByCartId(@Param("memberId") Long memberId,@Param("cartIds") List<Long> cartIds);
}
