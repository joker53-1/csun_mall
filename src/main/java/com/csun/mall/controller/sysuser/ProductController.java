package com.csun.mall.controller.sysuser;

import com.csun.mall.domain.ProductImg;
import com.csun.mall.domain.ProductItem;
import com.csun.mall.pojo.dto.ProductParam;
import com.csun.mall.pojo.dto.ProductQueryDTO;
import com.csun.mall.service.ProductService;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/2 14:38
 */
@RestController
@RequestMapping("/api/product")
@Api(tags = "后台商品管理")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("创建商品")
    @PostMapping(value = "/create")
//    @ApiImplicitParam(name="productParam",dataType = "ProductParam")
    public ResponseData create(@RequestBody ProductParam productParam, BindingResult bindingResult) {
        int count = productService.create(productParam);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping(value = "/updateInfo")
    public ResponseData<ProductParam> getUpdateInfo(Long id) {
        ProductParam productParam = productService.getUpdateInfo(id);
        return ResponseData.success(productParam);
    }

    @ApiOperation("更新商品")
    @PutMapping(value = "/update")
    public ResponseData update(@RequestBody ProductParam productParam, BindingResult bindingResult) {
        int count = productService.update(productParam.getId(), productParam);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation("查询商品列表")
    @GetMapping(value = "/list")
    public ResponseData<PageResult<ProductQueryDTO>> getList(String keyword,Boolean enable,Long cateId,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageResult<ProductQueryDTO> productList = productService.list(keyword,enable, cateId,pageNum,pageSize);
        return ResponseData.success(productList);
    }


    @ApiOperation("批量修改删除状态")
    @PutMapping(value = "/updateEnable")
    @ApiImplicitParam(name = "ids", value = "ids", dataTypeClass = List.class, paramType = "query")
    public ResponseData updateEnable(@RequestParam List<Long> ids, Boolean enable) {
        int count = productService.updateEnable(ids, enable);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

}
