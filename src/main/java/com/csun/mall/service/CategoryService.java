package com.csun.mall.service;

import com.csun.mall.domain.ProductCategory;
import com.csun.mall.mapper.ProductCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/7 10:45
 */
@Service
@Transactional
public class CategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;


    public List<ProductCategory> getTypeList() {
        return productCategoryMapper.selectAll();
    }
}
