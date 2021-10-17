package com.csun.mall.service;

import com.csun.mall.common.tools.PojoConvertTool;
import com.csun.mall.domain.ProductCategory;
import com.csun.mall.mapper.ProductCategoryDao;
import com.csun.mall.mapper.ProductCategoryMapper;
import com.csun.mall.pojo.dto.ProductCategoryWithChildren;
import com.csun.mall.pojo.dto.ProductQueryDTO;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    public int create(ProductCategory productCategoryParam) {
        ProductCategory productCategory = new ProductCategory();
        int count = productCategoryMapper.insertSelective(productCategory);
        return count;
    }

    public int update(Long id, ProductCategory productCategoryParam) {
        productCategoryParam.setId(id);
        return productCategoryMapper.updateByPrimaryKeySelective(productCategoryParam);
    }

    public int delete(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id);
    }

    public PageResult<ProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize) {
        Example example = new Example(ProductCategory.class);
        example.createCriteria().andEqualTo("parentId",parentId);
        PageHelper.startPage(pageNum,pageSize);
        List<ProductCategory> list = productCategoryMapper.selectByExample(example);
//        return PageResult.from(list.stream().filter(e ->e.getName().matches(".*"+name+".*")).collect(Collectors.toList()), ProductQueryDTO.class);
        return PageResult.from(list,ProductCategory.class);
    }

    public List<ProductCategory> getList(Long parentId) {
        Example example = new Example(ProductCategory.class);
        example.createCriteria().andEqualTo("parentId",parentId);

        List<ProductCategory> list = productCategoryMapper.selectByExample(example);
//        return PageResult.from(list.stream().filter(e ->e.getName().matches(".*"+name+".*")).collect(Collectors.toList()), ProductQueryDTO.class);
        return list;
    }
    public List<Long> getIdList(Long parentId) {
        Example example = new Example(ProductCategory.class);
        example.createCriteria().andEqualTo("parentId",parentId);

        return productCategoryMapper.selectByExample(example).stream().map(ProductCategory::getId).collect(Collectors.toList());

    }

    public List<ProductCategoryWithChildren> listWithChildren() {
        return productCategoryDao.listWithChildren();
    }

    public ProductCategory getParent(Long typeCode){
        return productCategoryMapper.selectByPrimaryKey(typeCode);
    }
    public List<ProductCategory> getTypeList() {
        Example example = new Example(ProductCategory.class);
        example.createCriteria().andEqualTo("parentId",0);
        return productCategoryMapper.selectByExample(example);
    }
}
