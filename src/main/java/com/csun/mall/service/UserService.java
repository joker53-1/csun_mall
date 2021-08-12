package com.csun.mall.service;

import com.csun.mall.dto.SysUserDTO;
import com.csun.mall.mapper.SysUserLoginLogMapper;
import com.csun.mall.mapper.SysUserMapper;
import com.csun.mall.mapper.SysUserTokenMapper;
import com.csun.mall.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SysUserLoginLogMapper sysUserLoginLogMapper;

    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    
    public SysUser getAdminByUsername(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username",username);
        SysUser user = sysUserMapper.selectOneByExample(example);
        return user;
    }

    
    public SysUser register(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        sysUser.setCreateTime(new Date());
        sysUser.setEnable(true);
        sysUser.setSort(1);
        //查询是否有相同用户名的用户
        Example userExample = new Example(SysUser.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", sysUserDTO.getUsername());
        SysUser result = sysUserMapper.selectOneByExample(userExample);
        if (result!=null) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = new BCryptPasswordEncoder().encode(sysUser.getPassword());
        sysUser.setPassword(encodePassword);
        sysUserMapper.insert(sysUser);
        sysUser.setPassword(null);
        return sysUser;
    }

    
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            SysUser sysUser = authenticationService.loadUserByUsername(username);
            log.info(sysUser.getPassword());
            log.info("ijdifj"+new BCryptPasswordEncoder().encode(password));
            if(!new BCryptPasswordEncoder().matches(password,sysUser.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }

            insertLoginLog(username,null,null);
            authenticationService.generalUserToken(sysUser,null);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    public void insertLoginLog(String username,String ip,String userAgent) {
        SysUser sysUser = getAdminByUsername(username);
        if(sysUser==null) return;
        SysUserLoginLog loginLog = new SysUserLoginLog();
        loginLog.setUserId(sysUser.getId());
        loginLog.setCreateTime(new Date());
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(ip);
        loginLog.setUserAgent(userAgent);
        sysUserLoginLogMapper.insert(loginLog);
    }


    
    public String refreshToken(String oldToken) {
        return null;
    }

    
    public SysUser getItem(Long id) {
        return null;
    }

    
    public List<SysUser> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    
    public int update(Long id, SysUser admin) {
        return 0;
    }

    
    public int delete(Long id) {
        return 0;
    }

    
    public int updateRole(Long adminId, List<Long> roleIds) {
        return 0;
    }

    
    public List<SysRole> getRoleList(Long adminId) {
        return null;
    }

    
    public List<SysResource> getResourceList(Long adminId) {
        return null;
    }

    
    public int updatePermission(Long adminId, List<Long> permissionIds) {
        return 0;
    }

    
    public List<SysPermission> getPermissionList(Long adminId) {
        return null;
    }

}
