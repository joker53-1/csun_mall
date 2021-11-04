package com.csun.mall.controller.sysuser;

import com.csun.mall.controller.portal.interceptor.UserTokenInterceptor;
import com.csun.mall.pojo.dto.OrdersDTO;
import com.csun.mall.service.OrderService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Joker Zheng
 * @create 2021/11/4 9:34
 */

@RestController
@Api(tags = "后台订单管理")
@RequestMapping("/api/order")
public class OrderManageController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/query")
    public ResponseData<PageResult<OrdersDTO>> query(PageParam pageParam){
        return ResponseData.success(orderService.getOrder(pageParam));
    }

    @PostMapping("/change_status")
    public ResponseData changeStatus(int status,Long orderId){
        int res = orderService.changeStatus(orderId,status);
        if(res>0){
            return ResponseData.success();
        }
        return ResponseData.failure();
    }
}
