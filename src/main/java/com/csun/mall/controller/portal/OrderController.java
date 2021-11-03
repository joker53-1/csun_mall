package com.csun.mall.controller.portal;

import com.csun.mall.controller.portal.interceptor.UserTokenInterceptor;
import com.csun.mall.pojo.dto.OrdersDTO;
import com.csun.mall.service.OrderService;
import com.csun.mall.service.ShopCartService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/11/1 9:55
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopCartService shopCartService;

    @GetMapping("total")
    public ResponseData<BigDecimal> totalPrice(@RequestParam("cartIds") List<Long> cartIds){
        if(cartIds!=null&&cartIds.size()!=0){
            return ResponseData.success(orderService.getTotal(cartIds));
        }
        return ResponseData.success(new BigDecimal(0));
    }

    @PostMapping("/create")
    public ResponseData create(@RequestParam("cartIds") List<Long> cartIds){
        Long memberId = UserTokenInterceptor.userId.get();
        if(memberId!=null&&cartIds!=null){
            int res = orderService.createOrder(memberId,cartIds);
            if(res>0){
                shopCartService.deleteProduct(cartIds);
                return ResponseData.success();
            }
        }
        return ResponseData.failure();
    }

    @GetMapping("/query")
    public ResponseData<PageResult<OrdersDTO>> query(PageParam pageParam){
        Long memberId = UserTokenInterceptor.userId.get();
        if(memberId!=null){
            return ResponseData.success(orderService.getOrder(memberId,pageParam));
        }
        return ResponseData.failure();
    }


}
