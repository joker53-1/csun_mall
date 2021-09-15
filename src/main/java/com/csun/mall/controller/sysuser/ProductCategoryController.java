package com.csun.mall.controller.sysuser;

import com.csun.mall.pojo.dto.ProductCategoryWithChildren;
import com.csun.mall.service.CategoryService;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/15 15:54
 */
@RestController
@RequestMapping("productCategory")
@Api(tags = "后台商品分类管理")
public class ProductCategoryController {
    @Autowired
    private CategoryService categoryService;

//    @ApiOperation("添加产品分类")
//    @PostMapping(value = "/create")
//    @ResponseBody
//    public ResponseData create(@Validated @RequestBody ProductCategoryParam productCategoryParam,
//                               BindingResult result) {
//        int count = categoryService.create(productCategoryParam);
//        if (count > 0) {
//            return CommonResult.success(count);
//        } else {
//            return CommonResult.failed();
//        }
//    }

//    @ApiOperation("修改商品分类")
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult update(@PathVariable Long id,
//                               @Validated
//                               @RequestBody PmsProductCategoryParam productCategoryParam,
//                               BindingResult result) {
//        int count = productCategoryService.update(id, productCategoryParam);
//        if (count > 0) {
//            return CommonResult.success(count);
//        } else {
//            return CommonResult.failed();
//        }
//    }

//    @ApiOperation("分页查询商品分类")
//    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
//                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
//                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//        List<PmsProductCategory> productCategoryList = productCategoryService.getList(parentId, pageSize, pageNum);
//        return CommonResult.success(CommonPage.restPage(productCategoryList));
//    }
//
//
//    @ApiOperation("删除商品分类")
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult delete(@PathVariable Long id) {
//        int count = productCategoryService.delete(id);
//        if (count > 0) {
//            return CommonResult.success(count);
//        } else {
//            return CommonResult.failed();
//        }
//    }



    @ApiOperation("查询所有一级分类及子分类")
    @GetMapping(value = "/list/withChildren")
    @ResponseBody
    public ResponseData<List<ProductCategoryWithChildren>> listWithChildren() {
        List<ProductCategoryWithChildren> list = categoryService.listWithChildren();
        return ResponseData.success(list);
    }
}
