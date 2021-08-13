package com.csun.mall.config.handler;

import com.csun.mall.domain.SysUser;
import com.csun.mall.pojo.dto.UserTokenDTO;
import com.csun.mall.service.UserService;
import com.csun.mall.web.response.RESPONSE_STATUS;
import com.csun.mall.web.response.ResponseData;
import com.csun.mall.pojo.dto.SysUserLoginDTO;
import com.csun.mall.domain.SysUserToken;
import com.csun.mall.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cxr
 */
@Component
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationHandler implements AuthenticationSuccessHandler, LogoutSuccessHandler, AuthenticationFailureHandler, AuthenticationEntryPoint, AccessDeniedHandler {


    private final AuthenticationService authenticationService;
    private final UserService userService;
    @Autowired
    public AuthenticationHandler(AuthenticationService authenticationService,UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    /**
     *
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SysUserToken loginToken = authenticationService.getLoginToken(authentication);
        SysUser user = userService.getAdminByUserId(loginToken.getUserId());
        UserTokenDTO userTokenDTO = UserTokenDTO.builder().token(loginToken.getToken())
                .deviceId(loginToken.getDeviceId()).enable(user.getEnable()).icon(user.getIcon()).createTime(user.getCreateTime()).id(user.getId())
                .mobile(user.getMoblie()).name(user.getName()).nickName(user.getNickName()).sort(user.getSort()).username(user.getUsername()).build();
        ResponseEntity.ok(userTokenDTO);
        ResponseData.success("登录成功！",userTokenDTO).write(response);
    }

    /**
     *
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SysUserToken loginToken = this.authenticationService.getTokenByRequest(request);
        if (loginToken != null) {
            this.authenticationService.revokeUserToken(loginToken);
        }
        SecurityContextHolder.clearContext();
        ResponseData.success(new SysUserLoginDTO()).write(response);
    }

    /**
     *
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        if (e instanceof BadCredentialsException) {
            ResponseData.failure(RESPONSE_STATUS.BAD_CREDENTIALS_ERROR, RESPONSE_STATUS.BAD_CREDENTIALS_ERROR.value).write(response);
        }
    }

    /**
     *
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ResponseData.failure(RESPONSE_STATUS.UNAUTHORIZED_ERROR, RESPONSE_STATUS.UNAUTHORIZED_ERROR.value).write(response);
    }

    /**
     *
     */
    @Override
    @ExceptionHandler(value = AccessDeniedException.class)
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        ResponseData.failure(RESPONSE_STATUS.ACCESS_DENIED_ERROR, RESPONSE_STATUS.ACCESS_DENIED_ERROR.value).write(response);
    }
}