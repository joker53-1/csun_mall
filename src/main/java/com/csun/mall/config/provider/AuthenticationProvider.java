package com.csun.mall.config.provider;


import com.csun.mall.domain.SysDevice;
import com.csun.mall.domain.SysUser;
import com.csun.mall.domain.SysUserToken;
import com.csun.mall.service.AuthenticationService;
import com.csun.mall.service.DeviceService;
import com.csun.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    /**
     *
     */

    @Autowired
    private DeviceService deviceService;

    /**
     *
     */
    @Resource
    AuthenticationService authenticationService;

    @Autowired
    private UserService userService;
//    @Autowired
//    public AuthenticationProvider(DeviceService deviceService) {
//        this.deviceService = deviceService;
//    }

    /**
     *
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    /**
     *
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        SysUser user = authenticationService.loadUserByUsername(username);
        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException(username);
        }
        UserAuthenticationDetails details = (UserAuthenticationDetails) authentication.getDetails();
        SysDevice device = deviceService.apply(details.deviceId, details.ip, details.userAgent);
        SysUserToken token = authenticationService.generalUserToken(user, device);
        userService.insertLoginLog(username,details.ip,details.userAgent);
        return new UsernamePasswordAuthenticationToken(token, password, null);
    }
}