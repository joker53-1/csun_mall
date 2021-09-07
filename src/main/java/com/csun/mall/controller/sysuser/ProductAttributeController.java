package com.csun.mall.controller.sysuser;

import com.csun.mall.domain.ProductAttribute;
import com.csun.mall.pojo.dto.ProductAttributeParam;
import com.csun.mall.service.ProductAttributeService;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性管理Controller
 * Created by macro on 2018/4/26.
 */
@RestController
@Api(tags = "商品属性管理")
@RequestMapping("/productAttribute")
public class ProductAttributeController {
    @Autowired
    private ProductAttributeService productAttributeService;

    @ApiOperation("添加商品属性信息")
    @PostMapping(value = "/create")
    public ResponseData create( ProductAttributeParam productAttributeParam, BindingResult bindingResult) {
        int count = productAttributeService.create(productAttributeParam);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation("修改商品属性信息")
    @PostMapping(value = "/update")
    public ResponseData update(Long id, ProductAttributeParam productAttributeParam, BindingResult bindingResult) {
        int count = productAttributeService.update(id, productAttributeParam);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation("查询单个商品属性")
    @GetMapping(value = "/get")
    public ResponseData<ProductAttribute> getItem(Long id) {
        ProductAttribute productAttribute = productAttributeService.getItem(id);
        return ResponseData.success(productAttribute);
    }

    @ApiOperation("批量删除商品属性")
    @PostMapping(value = "/delete")
    @ApiImplicitParam(name = "ids",value = "ids",dataTypeClass = List.class, paramType = "query")
    public ResponseData delete(@RequestParam List<Long> ids) {
        int count = productAttributeService.delete(ids);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation("获取商品属性")
    @GetMapping(value = "/list")
    public ResponseData<List<ProductAttribute>> getList() {
        List<ProductAttribute> productAttrList = productAttributeService.getProductAttrList();
        return ResponseData.success(productAttrList);
    }
}
