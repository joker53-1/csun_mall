//package com.csun.mall.config.provider;
//
//import com.csun.mall.domain.SysUser;
//import com.macro.mall.model.UmsAdmin;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Arrays;
//import java.util.Collection;
//
///**
// * SpringSecurity需要的用户详情
// */
//public class BaseUserDetails implements UserDetails {
//    private SysUser sysUser;
//
//    public BaseUserDetails(SysUser sysUser) {
//        this.sysUser = sysUser;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        //返回当前用户的权限
//        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
//    }
//
//    @Override
//    public String getPassword() {
//        return sysUser.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return sysUser.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
