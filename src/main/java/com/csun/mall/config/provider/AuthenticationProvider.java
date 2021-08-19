package com.csun.mall.config.provider;


import com.csun.mall.domain.SysDevice;
import com.csun.mall.domain.SysUser;
import com.csun.mall.domain.SysUserToken;
import com.csun.mall.service.AuthenticationService;
import com.csun.mall.service.SysDeviceService;
import com.csun.mall.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    /**
     *
     */

    @Autowired
    private SysDeviceService sysDeviceService;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     *
     */
    @Resource
    AuthenticationService authenticationService;

    @Autowired
    private SysUserService sysUserService;
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException(username);
        }
        if(!user.getEnable()){
            throw new DisabledException(username);
        }
        UserAuthenticationDetails details = (UserAuthenticationDetails) authentication.getDetails();
        SysDevice device = sysDeviceService.apply(details.deviceId, details.ip, details.userAgent);
        SysUserToken token = authenticationService.generalUserToken(user, device);
        sysUserService.insertLoginLog(username,details.ip,details.userAgent);
        return new UsernamePasswordAuthenticationToken(token, password, userDetails.getAuthorities());
    }
}