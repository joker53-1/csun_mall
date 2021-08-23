package com.csun.mall.controller;

import com.csun.mall.common.tools.MobileEmailTool;
import com.csun.mall.common.tools.PojoConvertTool;
import com.csun.mall.common.tools.RedisOperator;
import com.csun.mall.domain.CsrMember;
import com.csun.mall.pojo.dto.CsrMemberDTO;
import com.csun.mall.pojo.dto.UserTokenDTO;
import com.csun.mall.service.CsrMemberService;
import com.csun.mall.service.MailService;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    private static final String USER_PRE = "user_";

    @Autowired
    private MailService mailService;

    @Autowired
    private CsrMemberService csrMemberService;

    @Autowired
    private RedisOperator redisOperator;


    @ApiOperation(value = "获取验证码", notes = "获取验证码", httpMethod = "GET")
    @GetMapping("/verification")
    public ResponseData getVerification(String email) {
        if (!MobileEmailTool.checkEmailIsOk(email)) {
            return ResponseData.failure("邮箱格式出错！");
        }
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        mailService.send(email, code);
        String key = USER_PRE + email;
        redisOperator.set(key, code, 3600);
        return ResponseData.success();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public ResponseData<CsrMemberDTO> regist(String username, String email, String password, String code) {
        if (username.contains(" ")) {
            return ResponseData.failure("不能含有空格");
        }
        if (!MobileEmailTool.checkEmailIsOk(email)) {
            return ResponseData.failure("邮箱格式出错！");
        }
        if (MobileEmailTool.checkEmailIsOk(username) && username.matches("^[0-9]*$")) {
            return ResponseData.failure("用户名不能是邮箱或者手机号格式！");
        }
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return ResponseData.failure("用户名或密码不能为空");
        }
        CsrMember csrMember = csrMemberService.queryByUsername(username);
        if (csrMember != null) {
            return ResponseData.failure("用户名已经存在");
        }
        if (password.length() < 6) {
            return ResponseData.failure("密码长度不能少于6位");
        }
        if (username.equals(email)) {
            return ResponseData.failure("邮箱与用户名一致");
        }

        String verify = redisOperator.get(USER_PRE + email);
        if (StringUtils.isNotBlank(verify) && verify.equals(code)) {
            redisOperator.del(USER_PRE + email);
            CsrMember userResult = csrMemberService.createUser(username, email, password);
            CsrMemberDTO csrMemberDTO = PojoConvertTool.convert(userResult, CsrMemberDTO.class);
            return ResponseData.success(csrMemberDTO);
        }
        return ResponseData.failure("验证错误，请重新注册！");


    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public ResponseData login(@ApiParam(value = "用户名或邮箱") String keyword, String password,
                              Integer flag,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        //0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(keyword) ||
                StringUtils.isBlank(password)) {
            return ResponseData.failure("用户名或密码不能为空");
        }

        //1.实现登录
        CsrMember csrMember = csrMemberService.queryUserForLogin(keyword, password, flag);

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
        return ResponseData.success();
    }

    @GetMapping("a")
    public void testSend() {
        csrMemberService.createUser("zheng", "zheng", "123456");
        String to = "1589334904@qq.com";
//        mailService.send(to, UUID.randomUUID().toString().toUpperCase());
    }
}
