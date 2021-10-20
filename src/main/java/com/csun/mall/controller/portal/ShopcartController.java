package com.csun.mall.controller.portal;

import com.csun.mall.pojo.dto.ShopCartDTO;
import com.csun.mall.service.ShopCartService;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseData<List<ShopCartDTO>> list(Long memberId){
        if(memberId==null){
            return ResponseData.failure("未登录");
        }
        return ResponseData.success(shopCartService.list(memberId));
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseData add(Long memberId, Long productId, Integer count){
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
    public ResponseData update(Long id, Integer count){
        if(id==null||count==null){
            return ResponseData.failure();
        }
        int res = shopCartService.update(id,count);
        if (res > 0) {
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
    public ResponseData deleteProduct(Long memberId){
        if(memberId!=null){
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
    public ResponseData<Integer> getCartProductCount(Long memberId){

        //购物车逻辑
        return ResponseData.success(shopCartService.getCartProductCount(memberId));
    }

}
