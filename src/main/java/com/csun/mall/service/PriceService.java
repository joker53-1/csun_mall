package com.csun.mall.service;

import com.csun.mall.domain.ProductLadderPrice;
import com.csun.mall.mapper.ProductLadderPriceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/7 16:47
 */
@Service
@Transactional
public class PriceService {

    @Resource
    private ProductLadderPriceMapper productLadderPriceMapper;

    public List<ProductLadderPrice> getList(Long pid){
        Example example = new Example(ProductLadderPrice.class);
        example.createCriteria().andEqualTo("productId",pid);
        return productLadderPriceMapper.selectByExample(example);
    }

    /**
     * 批量更新商品库存信息
     */
    public void update(Long pid, List<ProductLadderPrice> productLadderPrices){
        Example example = new Example(ProductLadderPrice.class);
        example.createCriteria().andEqualTo("productId",pid);
        productLadderPriceMapper.deleteByExample(example);
        productLadderPrices.forEach(e->e.setProductId(pid));
        productLadderPriceMapper.insertList(productLadderPrices);
    }

}
