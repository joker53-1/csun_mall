package com.csun.mall.service;

import com.csun.mall.domain.CsrMemberCart;
import com.csun.mall.domain.OrderItems;
import com.csun.mall.domain.Orders;
import com.csun.mall.mapper.*;
import com.csun.mall.pojo.dto.OrdersDTO;
import com.csun.mall.pojo.dto.ShopCartDTO;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/11/1 10:03
 */
@Service
public class OrderService {

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderItemsMapper orderItemsMapper;

    @Resource
    private CsrMemberCartMapper cartMapper;

    @Resource
    private ShopCartDaoMapper shopCartDaoMapper;

    @Resource
    private OrdersDao ordersDao;

    public BigDecimal getTotal(List<Long> cartIds){
        Example example = new Example(CsrMemberCart.class);
        example.createCriteria().andIn("id",cartIds);
        List<CsrMemberCart> carts = cartMapper.selectByExample(example);
        BigDecimal bigDecimal = carts.stream().map(e -> new BigDecimal(e.getQuantity()).multiply(e.getCurPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);
        return bigDecimal;
    }

    public int createOrder(Long memberId,List<Long> cartIds){
        List<ShopCartDTO> carts = shopCartDaoMapper.getcartListByCartId(memberId,cartIds);
        if(carts!=null) {
            BigDecimal bigDecimal = carts.stream().map(e -> new BigDecimal(e.getQuantity()).multiply(e.getCurPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);
            Orders orders = new Orders();
            orders.setTotalAmount(bigDecimal);
            orders.setMemberId(memberId);
            orders.setCreatedTime(new Date());
            orders.setUpdatedTime(new Date());
            if(ordersMapper.insert(orders)<=0)
                return -1;
            for(ShopCartDTO shopCartDTO:carts){
                OrderItems orderItems = new OrderItems();
                orderItems.setOrderId(orders.getId());
                orderItems.setItemImg(shopCartDTO.getPic());
                orderItems.setQuantity(shopCartDTO.getQuantity());
                orderItems.setProductId(shopCartDTO.getProductId());
                orderItems.setPrice(shopCartDTO.getCurPrice());
                orderItems.setItemName(shopCartDTO.getProductName());
                if(orderItemsMapper.insert(orderItems)<=0)
                    return -1;
            }
            return 1;
        }
        return -1;
    }

    public PageResult<OrdersDTO> getOrder(Long memberId, PageParam param){
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        List<OrdersDTO> list = ordersDao.getOrders(memberId);
        return PageResult.from(list);
    }

    public PageResult<OrdersDTO> getOrder( PageParam param){
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        List<OrdersDTO> list = ordersDao.getOrders(null);
        return PageResult.from(list);
    }

    public List<OrdersDTO> getOrder(Long memberId){
        List<OrdersDTO> list = ordersDao.getOrders(memberId);
        return list;
    }

    public int changeStatus(Long orderId,int status){

        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        if(orders!=null){
            Orders newOrder = new Orders();
            newOrder.setId(orderId);
            newOrder.setStatus(status);
//            orders.setStatus(status);
            return ordersMapper.updateByPrimaryKeySelective(newOrder);
        }
        return -1;
    }
}
