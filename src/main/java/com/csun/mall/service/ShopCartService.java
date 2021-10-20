package com.csun.mall.service;

import com.csun.mall.domain.CsrMemberCart;
import com.csun.mall.domain.ProductItem;
import com.csun.mall.domain.ProductLadderPrice;
import com.csun.mall.mapper.CsrMemberCartMapper;
import com.csun.mall.mapper.ProductItemMapper;
import com.csun.mall.mapper.ShopCartDaoMapper;
import com.csun.mall.pojo.dto.ShopCartDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/10/18 14:45
 */
@Service
public class ShopCartService {

    @Autowired
    private PriceService priceService;
    
    @Resource
    private CsrMemberCartMapper cartMapper;

    @Resource
    private ShopCartDaoMapper shopCartDaoMapper;

    @Resource
    private ProductItemMapper productItemMapper;

    public int add(Long memberId, Long productId, Integer count){

        Example example = new Example(CsrMemberCart.class);
        example.createCriteria().andEqualTo("memberId",memberId).andEqualTo("productId",productId);
        CsrMemberCart cart = cartMapper.selectOneByExample(example);
        if (cart == null) {
            CsrMemberCart cartItem = new CsrMemberCart();
            cartItem.setProductId(productId);
            cartItem.setMemberId(memberId);
            cartItem.setQuantity(count);
            cartItem.setCurPrice(calculatePrice(productId,count));
            cartItem.setCreateTime(new Date());
            cartItem.setUpdateTime(new Date());
            return cartMapper.insert(cartItem);
        }
        else {
            Integer quantity = cart.getQuantity();
            count += quantity;
            CsrMemberCart newCart = new CsrMemberCart();
            newCart.setId(cart.getId());
            newCart.setQuantity(count);
            newCart.setCurPrice(calculatePrice(productId,count));
            newCart.setUpdateTime(new Date());
            return cartMapper.updateByPrimaryKeySelective(newCart);
        }

    }



    public int update(Long id, Integer count){
        CsrMemberCart cart = cartMapper.selectByPrimaryKey(id);
        if (cart != null) {
            CsrMemberCart newCart = new CsrMemberCart();
            newCart.setId(id);
            newCart.setQuantity(count);
            newCart.setCurPrice(calculatePrice(cart.getProductId(),count));
            newCart.setUpdateTime(new Date());
            return cartMapper.updateByPrimaryKeySelective(newCart);
        }
        return -1;
    }

    public int deleteProduct(List<Long> ids){
        Example example = new Example(CsrMemberCart.class);
        example.createCriteria().andIn("id",ids);
        return cartMapper.deleteByExample(example);
    }
    public int deleteProduct(Long memberId){
        Example example = new Example(CsrMemberCart.class);
        example.createCriteria().andEqualTo("memberId",memberId);
        return cartMapper.deleteByExample(example);
    }

    public List<ShopCartDTO> list(Long memberId){
        return shopCartDaoMapper.getcartList(memberId);
    }

    public Integer getCartProductCount(Long memberId){
        Example example = new Example(CsrMemberCart.class);
        example.createCriteria().andEqualTo("memberId",memberId);
        return cartMapper.selectCountByExample(example);
    }

    private BigDecimal calculatePrice(Long productId,Integer count){
        List<ProductLadderPrice> prices = priceService.getList(productId);
        if(ObjectUtils.isEmpty(prices)){
            ProductItem productItem = new ProductItem();
            productItem.setId(productId);
            return productItemMapper.selectByPrimaryKey(productItem).getPrice();
        }
        int index=0;
        while(index<prices.size()&&count>=prices.get(index).getCount()){
            index++;
        }
        return prices.get(index-1).getPrice();
    }
}
