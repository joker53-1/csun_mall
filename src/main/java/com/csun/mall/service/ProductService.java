package com.csun.mall.service;

import com.csun.mall.common.tools.PojoConvertTool;
import com.csun.mall.domain.*;
import com.csun.mall.mapper.*;
import com.csun.mall.pojo.dto.ProductParam;
import com.csun.mall.pojo.dto.ProductQueryDTO;
import com.csun.mall.pojo.dto.ProductsDTO;
import com.csun.mall.pojo.dto.SysRoleDTO;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Joker Zheng
 * @create 2021/8/24 14:17
 */
@Service
@Transactional
public class ProductService {

    @Resource
    private ProductItemDao productItemDao;

    @Resource
    private ProductAttributeValueMapper productAttributeValueMapper;
    @Resource
    private ProductItemMapper productItemMapper;

    @Resource
    private ProductImgMapper productImgMapper;

    @Resource
    private ProductLadderPriceMapper productLadderPriceMapper;

    public List<ProductsDTO> getProductList(Long catId) {
//        Example example = new Example(Products.class);
//        if(StringUtils.isNotBlank(catId)) {
//            example.createCriteria().andEqualTo("typeCode", catId);
//        }
//        example.setTableName("product_en");
//        String s = example.getDynamicTableName();
//        EntityTable et = EntityHelper.getEntityTable(Products.class);
//        productsMapper.selectByExample(example);
//        if(catId!=null)
        return productItemDao.getProducts(catId);
//        return null;
    }

    public List<ProductsDTO> getProductListByList(List<Long> catIds){
        if(ObjectUtils.isEmpty(catIds))
            return null;
        return productItemDao.getProductsByList(catIds);
    }

    public int create(ProductParam productParam) {

        ProductItem productItem = PojoConvertTool.convert(productParam, ProductItem.class);
        productItem.setCreateTime(new Date());
        productItem.setUpdateTime(new Date());
        productItemMapper.insertSelective(productItem);
//        productItem.setId(null);
        Long id = productItem.getId();
        List<ProductImg> imgList = productParam.getImage();
        imgList.forEach(e -> e.setProductId(id));
        imgList.forEach(e -> e.setCreateTime(new Date()));
        List<ProductAttributeValue> productAttributeValues = productParam.getProductAttributeValueList();
        productAttributeValues.forEach(e -> e.setProductId(id));
        List<ProductLadderPrice> prices = productParam.getPrices();
        prices.forEach(e->e.setProductId(id));
        if (productImgMapper.insertList(imgList) > 0
                && productAttributeValueMapper.insertList(productAttributeValues) > 0
                &&productLadderPriceMapper.insertList(prices)>0)
            return 1;
        return 0;
    }

    public int update(Long id, ProductParam productParam) {
        ProductItem productItem = PojoConvertTool.convert(productParam, ProductItem.class);
        productItem.setId(id);

        Example example1 = new Example(ProductAttributeValue.class);
        example1.createCriteria().andEqualTo("productId", id);
        productAttributeValueMapper.deleteByExample(example1);
        List<ProductAttributeValue> productAttributeValues = productParam.getProductAttributeValueList();
        productAttributeValues.forEach(e->e.setProductId(id));

        Example example2 = new Example(ProductImg.class);
        example1.createCriteria().andEqualTo("productId", id);
        productImgMapper.deleteByExample(example2);
        List<ProductImg> imgList = productParam.getImage();
        imgList.forEach(e->e.setProductId(id));

        Example example3 = new Example(ProductLadderPrice.class);
        example3.createCriteria().andEqualTo("productId",id);
        productLadderPriceMapper.deleteByExample(example3);
        List<ProductLadderPrice> prices = productParam.getPrices();
        prices.forEach(e->e.setProductId(id));

        if (productItemMapper.updateByPrimaryKeySelective(productItem) > 0 && productImgMapper.insertList(imgList) > 0
                && productAttributeValueMapper.insertList(productAttributeValues) > 0&&productLadderPriceMapper.insertList(prices)>0)
            return 1;
        return 0;
    }

    public ProductParam getUpdateInfo(Long id) {
        return productItemDao.getUpdateInfo(id);
    }


    public PageResult<ProductQueryDTO> list(String keyword,Integer pageNum, Integer pageSize) {
//        PageHelper.clearPage();
        PageHelper.startPage(pageNum,pageSize);
        List<ProductQueryDTO> list = productItemDao.getList(keyword);
//        return PageResult.from(list.stream().filter(e ->e.getName().matches(".*"+name+".*")).collect(Collectors.toList()), ProductQueryDTO.class);
        return PageResult.from(list,ProductQueryDTO.class);
    }

    public int updateEnable(List<Long> ids,Boolean enable){
        ProductItem productItem = new ProductItem();
        productItem.setEnable(enable);
        Example example = new Example(ProductItem.class);
        example.createCriteria().andIn("id",ids);
        return productItemMapper.updateByExampleSelective(productItem,example);
    }
}
