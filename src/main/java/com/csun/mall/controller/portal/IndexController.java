package com.csun.mall.controller.portal;

import com.csun.mall.domain.Products;
import com.csun.mall.service.CsrProductService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Joker Zheng
 * @create 2021/8/24 13:46
 */
@Controller
@RequestMapping("index")
public class IndexController {
    @Resource
    private CsrProductService productsService;


    @GetMapping("/products")
    public String getListProduct(String typeCode, Model model) {
        List<Products> list = productsService.getList(typeCode);
        model.addAttribute("productList", list);
        model.addAttribute("productTypeList", productsService.getTypeList());
        return "/index";
    }
    @GetMapping("/product")
    public String getProduct(@RequestParam Long id, Model model) {
        List<Products> list = productsService.getList(null);
        Products products = list.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(new Products());
        if (ObjectUtils.isEmpty(products)) {
            return "index";
        }
        List<Products> collect = list.stream().filter(e -> e.getTypeCode().equals(products.getTypeCode())).filter(e -> !e.getId().equals(id)).collect(Collectors.toList());
        List<Products> collect1 = list.stream().sorted(Comparator.comparing(Products::getSort)).filter(e -> !e.getId().equals(id)).collect(Collectors.toList());
        Collections.reverse(collect1);
        model.addAttribute("currentProduct", products);
        // 同类型其他产品
        model.addAttribute("productList", collect);
        model.addAttribute("currentHeaderType", "Products");
        model.addAttribute("productList1", collect1.subList(0,5));
        return "/product";

    }

    @GetMapping
    public String hello(){
        return "/index";
    }
    @GetMapping("p")
    public String pro(){
        return "/product";
    }
}
