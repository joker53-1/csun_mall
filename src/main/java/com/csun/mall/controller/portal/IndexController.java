package com.csun.mall.controller.portal;

import com.csun.mall.domain.Products;
import com.csun.mall.pojo.dto.ProductsDTO;
import com.csun.mall.service.CategoryService;
import com.csun.mall.service.ProductService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private ProductService productsService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/products")
    public String getListProduct(Long typeCode, Model model) {
        List<ProductsDTO> list = productsService.getProductList(typeCode);
        model.addAttribute("productList", list);
        model.addAttribute("productTypeList", categoryService.getTypeList());
        return "/index";
    }

    @GetMapping("/productsbytype")
    public String getListProductByType(Long typeCode, Model model) {
        List<ProductsDTO> list = productsService.getProductList(typeCode);
        model.addAttribute("productList", list);
        model.addAttribute("productTypeList", categoryService.getTypeList());
        return "/product_all";
    }
    @GetMapping("/product")
    public String getProduct(@RequestParam Long id, Model model) {
        List<ProductsDTO> list = productsService.getProductList(null);
        ProductsDTO productsDTO = list.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(new ProductsDTO());
        if(ObjectUtils.isEmpty(productsDTO)){
            return "index";
        }
        List<ProductsDTO> collect = list.stream().filter(e -> e.getCategoryId().equals(productsDTO.getCategoryId()))
                .filter(e -> !e.getId().equals(id)).collect(Collectors.toList());
        List<ProductsDTO> collect1 = list.stream().sorted(Comparator.comparing(ProductsDTO::getSort))
                .filter(e -> !e.getId().equals(id)).collect(Collectors.toList());
        Collections.reverse(collect1);
        model.addAttribute("currentProduct", productsDTO);
        model.addAttribute("productList",collect);
        model.addAttribute("currentHeaderType", "Products");
        //todo
        model.addAttribute("productList1", collect1.subList(0,1));
        return "/product";

    }

    @GetMapping("/contact")
    public String getContact(){
        return "/contact-us";
    }

    @GetMapping("/productlist")
    public String getProductlist(Long typeCode, Model model){
        List<ProductsDTO> list = productsService.getProductList(typeCode);
        model.addAttribute("productList", list);
        model.addAttribute("productTypeList", categoryService.getTypeList());
        return "/product_all";
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
