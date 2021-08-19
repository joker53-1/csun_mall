package com.csun.mall.config.filter;

import com.csun.mall.domain.SysPermission;
import com.csun.mall.domain.SysUserToken;
import com.csun.mall.mapper.SysUserRoleDao;
import com.csun.mall.service.AuthenticationService;
import com.csun.mall.web.response.RESPONSE_STATUS;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cxr
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException,IllegalStateException {
        SysUserToken loginToken = this.authenticationService.getTokenByRequest(request);
        List<GrantedAuthority> auths=null;
        if(loginToken!=null) {
            List<SysPermission> list = sysUserRoleDao.getPermissionList(loginToken.getUserId());
            //设置角色名称
             auths = list.stream().map(e -> new SimpleGrantedAuthority(e.getCode())).collect(Collectors.toList());
        }
        if (loginToken != null&&loginToken.getEnable()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginToken, loginToken.getToken(), auths);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}