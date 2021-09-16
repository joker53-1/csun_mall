package com.csun.mall.mapper;

import com.csun.mall.pojo.dto.ProductParam;
import com.csun.mall.pojo.dto.ProductQueryDTO;
import com.csun.mall.pojo.dto.ProductsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/2 17:04
 */
public interface ProductItemDao {
    List<ProductsDTO> getProducts(@Param("categoryId") Long categoryId);
    ProductParam getUpdateInfo(@Param("id") Long id);
    List<ProductQueryDTO> getList(@Param("keyword") String keyword);
}
