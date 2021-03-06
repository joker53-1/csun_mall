package com.csun.mall.controller.portal;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.io.IoUtil;
import com.csun.mall.controller.portal.interceptor.UserTokenInterceptor;
import com.csun.mall.domain.CsrMember;
import com.csun.mall.domain.ProductCategory;
import com.csun.mall.domain.ProductLadderPrice;
import com.csun.mall.pojo.dto.ProductCategoryWithChildren;
import com.csun.mall.pojo.dto.ProductsDTO;
import com.csun.mall.service.CategoryService;
import com.csun.mall.service.CsrMemberService;
import com.csun.mall.service.PriceService;
import com.csun.mall.service.ProductService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Joker Zheng
 * @create 2021/8/24 13:46
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private ProductService productsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PriceService priceService;



    @GetMapping("/index/registry")
    public String toRegistry(){
        return "/sign_up";
    }

    @GetMapping("/index/login")
    public String toLogin(){
        return "/sign_in";
    }

    @GetMapping("/")
    public String index(Long typeCode, Model model) {

        List<ProductsDTO> list = productsService.getProductListByList(categoryService.getIdList(7L));

        model.addAttribute("productList", list);
        model.addAttribute("productTypeList", categoryService.listWithChildren());
        return "/index";
    }

    @GetMapping("/products")
    public String getListProduct(Long typeCode, Model model) {
        List<ProductsDTO> list = productsService.getProductList(typeCode);
        model.addAttribute("productList", list);
        model.addAttribute("productTypeList", categoryService.listWithChildren());
        return "/index";
    }


    @GetMapping("/product")
    public String getProduct(@RequestParam Long id, Model model) {
        model.addAttribute("productTypeList", categoryService.listWithChildren());
        List<ProductsDTO> list = productsService.getProductList(null);
        ProductsDTO productsDTO = list.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(new ProductsDTO());
        if (ObjectUtils.isEmpty(productsDTO)) {
            return "/index";
        }
        List<ProductsDTO> collect = list.stream().filter(e -> e.getCategoryId().equals(productsDTO.getCategoryId()))
                .filter(e -> !e.getId().equals(id)).collect(Collectors.toList());
        List<ProductsDTO> collect1 = list.stream().sorted(Comparator.comparing(ProductsDTO::getSort))
                .filter(e -> !e.getId().equals(id)).collect(Collectors.toList());
        Collections.reverse(collect1);
        model.addAttribute("currentProduct", productsDTO);
        if(collect.size()>=3){
            collect = collect.subList(0,3);
        }
        model.addAttribute("productList", collect);
//        model.addAttribute("currentHeaderType", "Products");
        //todo
        if(collect1.size()>=1){
            collect1 = collect1.subList(0,1);
        }
        model.addAttribute("productList1", collect1);
        List<ProductLadderPrice> plp = priceService.getList(id);
        model.addAttribute("prices", plp);
        return "/product";

    }

    @GetMapping("/contact")
    public String getContact(Model model) {
        List<ProductsDTO> list = productsService.getProductList(null);
        model.addAttribute("productList", list);
        model.addAttribute("productTypeList", categoryService.listWithChildren());
        return "/contact-us";
    }

    @GetMapping("/productlist")
    public String getProductlist(Long typeCode, Model model) {

        ProductCategory category= categoryService.getParent(typeCode);
        Long parentCode = category.getParentId();
        model.addAttribute("rootCategory",category.getName());
        if(parentCode==0){
            List<ProductCategory> categories =  categoryService.getList(typeCode);
            model.addAttribute("productTypeList", categories);
            List<Long> codeList = categories.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<ProductsDTO> list = productsService.getProductListByList(codeList);
            model.addAttribute("productList", list);
//            typeCode=null;
        }
        else {
            model.addAttribute("productTypeList", categoryService.getList(parentCode));
            List<ProductCategoryWithChildren> categoryWithChildren = categoryService.listWithChildren();
            List<ProductsDTO> list = productsService.getProductList(typeCode);
            model.addAttribute("productList", list);
        }
        return "/product_all";
    }

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(100, 27);
        captcha.setGenerator(randomGenerator);
        captcha.createCode();
        request.getSession().removeAttribute("captcha");
        request.getSession().setAttribute("captcha", captcha.getCode());
        IoUtil.write(response.getOutputStream(), true, captcha.getImageBytes());
    }

}
