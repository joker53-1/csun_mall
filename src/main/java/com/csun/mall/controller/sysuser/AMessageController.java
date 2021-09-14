package com.csun.mall.controller.sysuser;

import com.csun.mall.pojo.dto.ProductsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/9/9 14:59
 */

@Controller
@RequestMapping("index")
public class AMessageController {
    @GetMapping("/chat")
    public String getListProduct(Long typeCode, Model model) {
        return "/Chat";
    }
}
