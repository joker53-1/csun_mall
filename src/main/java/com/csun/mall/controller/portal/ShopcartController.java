package com.csun.mall.controller.portal;

import com.csun.mall.controller.portal.interceptor.UserTokenInterceptor;
import com.csun.mall.pojo.dto.ShopCartDTO;
import com.csun.mall.service.ShopCartService;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Author Joker Zheng
 * @create 2021/10/18 14:43
 */
@Api(tags = "用户购物车管理")
@RestController
@RequestMapping("customer/shopcart")
public class ShopcartController {

    @Autowired
    private ShopCartService shopCartService;


    @GetMapping("list")
    @ResponseBody
    public ResponseData<List<ShopCartDTO>> list(){
        Long memberId = UserTokenInterceptor.userId.get();
        if(memberId==null){
            return ResponseData.failure("未登录");
        }
        return ResponseData.success(shopCartService.list(memberId));
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseData add( Long productId, Integer count){
        Long memberId = UserTokenInterceptor.userId.get();
        if(memberId==null||productId==null||count==null){
            return ResponseData.failure();
        }
        int res = shopCartService.add(memberId,productId,count);
        if (res > 0) {
            return ResponseData.success(res);
        } else {
            return ResponseData.failure();
        }
    }

    @PutMapping("update")
    @ResponseBody
    public ResponseData<BigDecimal> update(Long id, Integer count){
        if(id==null||count==null){
            return ResponseData.failure();
        }
        BigDecimal res = shopCartService.update(id,count);
        if (!Objects.equals(res, new BigDecimal(-1))) {
            return ResponseData.success(res);
        } else {
            return ResponseData.failure();
        }
    }

    @DeleteMapping("delete")
    @ResponseBody
    @ApiImplicitParam(name = "ids", value = "ids", dataTypeClass = List.class, paramType = "query")
    public ResponseData deleteProduct(@RequestParam List<Long> ids){
        if(ids.size()==0){
            return ResponseData.failure();
        }
        int res = shopCartService.deleteProduct(ids);
        if (res > 0) {
            return ResponseData.success(res);
        } else {
            return ResponseData.failure();
        }
    }

    @DeleteMapping("deleteAll")
    @ResponseBody
    public ResponseData deleteProduct(){
        Long memberId = UserTokenInterceptor.userId.get();
        if(memberId==null){
            return ResponseData.failure();
        }
        int res = shopCartService.deleteProduct(memberId);
        if (res > 0) {
            return ResponseData.success(res);
        } else {
            return ResponseData.failure();
        }
    }

    @GetMapping("getcartproductnum")
    @ResponseBody
    public ResponseData<Integer> getCartProductCount(){
        Long memberId = UserTokenInterceptor.userId.get();

        //购物车逻辑
        return ResponseData.success(shopCartService.getCartProductCount(memberId));
    }

}
