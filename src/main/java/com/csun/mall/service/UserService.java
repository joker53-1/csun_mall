package com.csun.mall.service;

import cn.hutool.core.util.StrUtil;
import com.csun.mall.domain.*;
import com.csun.mall.pojo.dto.SysUserDTO;
import com.csun.mall.mapper.SysUserLoginLogMapper;
import com.csun.mall.mapper.SysUserMapper;
import com.csun.mall.mapper.SysUserTokenMapper;
import com.csun.mall.web.response.PagedGridResult;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService extends BaseService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SysUserLoginLogMapper sysUserLoginLogMapper;

    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    public SysUser getAdminByUserId(Long userId){
        return sysUserMapper.selectByPrimaryKey(userId);
    }

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
        return sysUserMapper.selectByPrimaryKey(id);
    }

    
    public PagedGridResult list(String keyword, Integer page, Integer pageSize) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isBlank(keyword)) {
            criteria.andEqualTo( "username",keyword );
            example.or(example.createCriteria().andEqualTo("nickName",keyword ));
        }
        //mabatis-pagehelper
        /**
         *page:第几页
         * pageSize:每页显示条数
         **/
        PageHelper.startPage(page,pageSize);

        List<SysUser> list = sysUserMapper.selectByExample(example);
        return setterPagedGrid(list,page);
    }

    
    public int update(Long id, SysUser admin) {
        admin.setId(id);
        SysUser rawAdmin = sysUserMapper.selectByPrimaryKey(id);
        if(rawAdmin.getPassword().equals(admin.getPassword())){
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
            }
        }
        admin.setCreateTime(new Date());
        int count = sysUserMapper.updateByPrimaryKeySelective(admin);
        return count;
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
