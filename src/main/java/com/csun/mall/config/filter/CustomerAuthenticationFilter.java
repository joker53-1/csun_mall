package com.csun.mall.config.filter;

import com.csun.mall.common.constant.SYS_CONSTANT;
import com.csun.mall.domain.CsrMemberToken;
import com.csun.mall.domain.SysPermission;
import com.csun.mall.domain.SysUserToken;
import com.csun.mall.mapper.SysUserRoleDao;
import com.csun.mall.service.AuthenticationService;
import com.csun.mall.service.CsrMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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


@Component
public class CustomerAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private CsrMemberService csrMemberService;

    private final AuthenticationService authenticationService;

    @Autowired
    public CustomerAuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException,IllegalStateException {
        String tokenStr = request.getHeader(SYS_CONSTANT.HEADER_TOKEN);
        CsrMemberToken loginToken=null;
        if (StringUtils.isNotBlank(tokenStr)) {
            loginToken=csrMemberService.getToken(tokenStr);
        }

        if (loginToken != null&&loginToken.getEnable()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginToken, loginToken.getToken(), null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}