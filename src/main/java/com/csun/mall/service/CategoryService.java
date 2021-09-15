package com.csun.mall.service;

import com.csun.mall.domain.ProductCategory;
import com.csun.mall.mapper.ProductCategoryDao;
import com.csun.mall.mapper.ProductCategoryMapper;
import com.csun.mall.pojo.dto.ProductCategoryWithChildren;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    @Resource
    private ProductCategoryDao productCategoryDao;

    public List<ProductCategoryWithChildren> listWithChildren() {
        return productCategoryDao.listWithChildren();
    }

    public List<ProductCategory> getTypeList() {
        Example example = new Example(ProductCategory.class);
        example.createCriteria().andEqualTo("parentId",0);
        return productCategoryMapper.selectByExample(example);
    }
}
