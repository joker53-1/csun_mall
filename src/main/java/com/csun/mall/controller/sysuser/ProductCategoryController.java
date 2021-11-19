package com.csun.mall.controller.sysuser;

import com.csun.mall.domain.ProductCategory;
import com.csun.mall.pojo.dto.ProductCategoryWithChildren;
import com.csun.mall.service.CategoryService;
import com.csun.mall.web.response.PageResult;
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
@RequestMapping("/api/productCategory")
@Api(tags = "后台商品分类管理")
public class ProductCategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("添加产品分类")
    @PostMapping(value = "/create")
    public ResponseData create(ProductCategory productCategoryParam) {
        int count = categoryService.create(productCategoryParam);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation("修改商品分类")
    @PutMapping(value = "/update")
    public ResponseData update(Long id,ProductCategory productCategoryParam) {
        int count = categoryService.update(id, productCategoryParam);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }

    @ApiOperation("根据id查询分类")
    @GetMapping(value = "/get")
    public ResponseData<ProductCategory> getById(Long id){
        return ResponseData.success(categoryService.getById(id));
    }

    @ApiOperation("分页查询商品分类")
    @GetMapping(value = "/list")
    public ResponseData<PageResult<ProductCategory>> getList(Long parentId,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageResult<ProductCategory> productCategoryList = categoryService.getList(parentId, pageNum,pageSize);
        return ResponseData.success(productCategoryList);
    }


    @ApiOperation("删除商品分类")
    @DeleteMapping(value = "/delete")
    public ResponseData delete(Long id) {
        int count = categoryService.delete(id);
        if (count > 0) {
            return ResponseData.success(count);
        } else {
            return ResponseData.failure();
        }
    }



    @ApiOperation("查询所有一级分类及子分类")
    @GetMapping(value = "/list/withChildren")
    public ResponseData<List<ProductCategoryWithChildren>> listWithChildren() {
        List<ProductCategoryWithChildren> list = categoryService.listWithChildren();
        return ResponseData.success(list);
    }
}
