package com.csun.mall.service;

import com.csun.mall.domain.Products;
import com.csun.mall.domain.ProductsType;
import com.csun.mall.mapper.ProductsMapper;
import com.csun.mall.mapper.ProductsTypeMapper;
import com.csun.mall.pojo.dto.vo.ProductsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/8/24 14:17
 */
@Service
@Transactional
public class CsrProductService {


    @Resource
    private ProductsMapper productsMapper;
    @Resource
    private ProductsTypeMapper productsTypeMapper;

    public List<Products> getList(String typeCode) {
        Example example = new Example(Products.class);
        example.setTableName("products_en");
        if(StringUtils.isNotBlank(typeCode)) {
            example.createCriteria().andEqualTo("typeCode", typeCode);
        }
        List<Products> products = productsMapper.selectByExample(example);
        return products;
    }

    public List<ProductsType> getTypeList(){
        return productsTypeMapper.selectAll();
    }

}
