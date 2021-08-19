package com.csun.mall.config.provider;

import com.csun.mall.domain.SysPermission;
import com.csun.mall.domain.SysUser;
import com.csun.mall.mapper.SysUserMapper;
import com.csun.mall.mapper.SysUserRoleDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Example example = new Example(SysUser.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("username", username);
        SysUser sysUser = sysUserMapper.selectOneByExample(example);
        List<SysPermission> list = sysUserRoleDao.getPermissionList(sysUser.getId());

        //设置角色名称
        List<GrantedAuthority> auths = list.stream().map(e -> new SimpleGrantedAuthority(e.getCode())).collect(Collectors.toList());

        if (sysUser != null ) {
            return new User(sysUser.getUsername(), sysUser.getPassword(), true, true, true, true, auths);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
