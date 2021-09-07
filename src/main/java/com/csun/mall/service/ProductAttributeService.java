package com.csun.mall.service;

import com.csun.mall.common.tools.PojoConvertTool;
import com.csun.mall.domain.ProductAttribute;
import com.csun.mall.mapper.ProductAttributeMapper;
import com.csun.mall.pojo.dto.ProductAttributeParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @Author Joker Zheng
 * @create 2021/9/3 14:09
 */
@Service
public class ProductAttributeService {

    @Resource
    private ProductAttributeMapper productAttributeMapper;

    public int create(ProductAttributeParam productAttributeParam) {
        ProductAttribute productAttribute = PojoConvertTool.convert(productAttributeParam,ProductAttribute.class);
        int count = productAttributeMapper.insertSelective(productAttribute);
        return count;
    }

    public int update(Long id, ProductAttributeParam productAttributeParam) {
        ProductAttribute productAttribute = PojoConvertTool.convert(productAttributeParam,ProductAttribute.class);
        productAttribute.setId(id);
        return productAttributeMapper.updateByPrimaryKeySelective(productAttribute);
    }

    public ProductAttribute getItem(Long id) {
        return productAttributeMapper.selectByPrimaryKey(id);
    }

    public int delete(List<Long> ids) {
        Example example = new Example(ProductAttribute.class);
        example.createCriteria().andIn("id",ids);
        int count = productAttributeMapper.deleteByExample(example);
        return count;
    }

    public List<ProductAttribute> getProductAttrList(){
        return productAttributeMapper.selectAll();
    }

}
