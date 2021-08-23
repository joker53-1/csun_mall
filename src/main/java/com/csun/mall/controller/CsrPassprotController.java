package com.csun.mall.controller;

import com.csun.mall.common.tools.RedisOperator;
import com.csun.mall.domain.CsrMember;
import com.csun.mall.service.CsrMemberService;
import com.csun.mall.service.MailService;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author Joker Zheng
 * @create 2021/8/21 10:09
 */
@Api(tags = "用户注册登录")
@RestController
@RequestMapping("customer/passport")
public class CsrPassprotController {

    @Autowired
    private MailService mailService;

    @Autowired
    private CsrMemberService csrMemberService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public ResponseData regist(String username,String email,String password) {
        if(StringUtils.isBlank(username)||
                StringUtils.isBlank(password)){
            return ResponseData.failure("用户名或密码不能为空");
        }
        boolean isExist = csrMemberService.queryUsernameIsExist(username);
        if(isExist){
            return ResponseData.failure("用户名已经存在");
        }
        if(password.length()<6){
            return ResponseData.failure("密码长度不能少于6位");
        }
        CsrMember userResult = csrMemberService.createUser(username,email,password);
        String code = String.valueOf((int)((Math.random()*9+1)*100000));
        mailService.send(email,code);
        String key = "user_"+email;
        redisOperator.set(key,code,3600);
        return ResponseData.success(userResult);
    }
    @ApiOperation(value = "用户验证", notes = "用户验证", httpMethod = "POST")
    @PostMapping("/verify")
    private ResponseData verify(Long id,String code){
        CsrMember csrMember = csrMemberService.queryMemberById(id);
        String verify = redisOperator.get("user_"+csrMember.getEmail());
        if(StringUtils.isNotBlank(verify)&&verify.equals(code)){
            int count = csrMemberService.activate(csrMember);
            if(count>0)
                return ResponseData.success("验证成功！");
        }
        //TODO 删除数据库记录
        return ResponseData.failure("验证错误，请重新注册！");
    }

//    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
//    @PostMapping("/login")
//    public JsonResult login(@RequestBody UserBO userBO,
//                            HttpServletRequest request,
//                            HttpServletResponse response) throws Exception{
//        String username = userBO.getUsername();
//        String password = userBO.getPassword();
//        //0.判断用户名和密码必须不为空
//        if(StringUtils.isBlank(username)||
//                StringUtils.isBlank(password)){
//            return JsonResult.errorMsg("用户名或密码不能为空");
//        }
//
//
//        //1.实现登录
//        Users userResult = userService.queryUserForLogin(username,
//                Md5Util.getMD5Str(password));
//
//        if(userResult==null){
//            return JsonResult.errorMsg("用户名或密码不正确");
//        }
//
////        userResult = setNullProperty(userResult);
//
//
//        //实现用户的redis会话
//        UsersVO usersVO = conventUsersVO(userResult);
//
//        CookieUtils.setCookie(request,response,"user",
//                JsonUtils.objectToJson(usersVO),true);
//
//        //生成用户token，存入redis会话
//        //同步购物车数据
//        synchShopcartData(userResult.getId(),request,response);
//        return JsonResult.ok(userResult);
//    }

    @GetMapping("a")
    public void testSend() {
        csrMemberService.createUser("zheng","zheng","123456");
        String to = "1589334904@qq.com";
//        mailService.send(to, UUID.randomUUID().toString().toUpperCase());
    }
}
