package com.csun.mall.controller.portal;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.controller.portal.interceptor.UserTokenInterceptor;
import com.csun.mall.domain.CsrMember;
import com.csun.mall.pojo.dto.OrdersDTO;
import com.csun.mall.service.CsrMemberService;
import com.csun.mall.service.OrderService;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Author Joker Zheng
 * @create 2021/10/31 9:41
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CsrMemberService csrMemberService;

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public String index(){
        return "/admin/admin";
    }

    @GetMapping("/info")
    public String getInfo(Model model){
        Long memberId = UserTokenInterceptor.userId.get();
        CsrMember member = csrMemberService.queryMemberById(memberId);
        model.addAttribute("member",member);
        return "/admin/information";
    }
    @GetMapping("/changePwd")
    public String changePwd(Model model){
//        Long memberId = UserTokenInterceptor.userId.get();
//        CsrMember member = csrMemberService.queryMemberById(memberId);
//        model.addAttribute("member",member);
        return "/admin/changePwd";
    }

    @GetMapping("/order")
    public String order(Model model){
        Long memberId = UserTokenInterceptor.userId.get();
        List<OrdersDTO> list = orderService.getOrder(memberId);
        model.addAttribute("orderList",list);
        return "/admin/order";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseData<CsrMember> update(String nickname, String phone, Integer gender, String city){
        Long id = UserTokenInterceptor.userId.get();
        CsrMember res = csrMemberService.updateInfo(id,nickname,phone, gender, city);
        if(res==null){
            return ResponseData.failure();
        }
        return ResponseData.success(res);
    }

    @PostMapping("/change")
    @ResponseBody
    public ResponseData change(String oldPassword,String password){
        Long id = UserTokenInterceptor.userId.get();
        int res = csrMemberService.change(id,oldPassword,password);
        if(res==0){
            return ResponseData.failure("原密码不正确");
        }
        else if(res<0){
            return ResponseData.failure("修改失败");
        }else {
            return ResponseData.success();
        }
    }
}
