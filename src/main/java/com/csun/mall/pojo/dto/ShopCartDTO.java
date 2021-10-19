package com.csun.mall.pojo.dto;

import com.csun.mall.domain.CsrMemberCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author Joker Zheng
 * @create 2021/10/19 21:55
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartDTO extends CsrMemberCart {
    public String pic;
    public String productName;
}
