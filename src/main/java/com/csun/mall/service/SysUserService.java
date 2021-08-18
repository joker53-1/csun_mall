package com.csun.mall.service;


import com.csun.mall.common.tools.PojoConvertTool;
import com.csun.mall.domain.*;
import com.csun.mall.mapper.*;
import com.csun.mall.pojo.dto.SysUserDTO;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserService {


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private SysUserLoginLogMapper sysUserLoginLogMapper;

    @Resource
    private SysUserTokenMapper sysUserTokenMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysUserPermissionMapper sysUserPermissionMapper;

    @Resource
    private SysUserRoleDao sysUserRoleDao;


    public SysUser getUserByUserId(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    public SysUser getUserByUsername(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", username);
        SysUser user = sysUserMapper.selectOneByExample(example);
        return user;
    }

    public SysUser getUserByMobile(String mobile) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("mobile", mobile);
        SysUser user = sysUserMapper.selectOneByExample(example);
        return user;
    }


    public SysUser register(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        sysUser.setCreateTime(new Date());
        sysUser.setEnable(true);
        //将密码进行加密操作
        String encodePassword = new BCryptPasswordEncoder().encode(sysUser.getPassword());
        sysUser.setPassword(encodePassword);
        sysUserMapper.insert(sysUser);
        sysUser.setPassword(null);
        return sysUser;
    }


    public String login(String username, String password) {
        String token = null;
        try {
            SysUser sysUser = authenticationService.loadUserByUsername(username);
            if (!new BCryptPasswordEncoder().matches(password, sysUser.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }

            insertLoginLog(username, null, null);
            authenticationService.generalUserToken(sysUser, null);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    public void insertLoginLog(String username, String ip, String userAgent) {
        SysUser sysUser = getUserByUsername(username);
        if (sysUser == null) return;
        SysUserLoginLog loginLog = new SysUserLoginLog();
        loginLog.setUserId(sysUser.getId());
        loginLog.setCreateTime(new Date());
        loginLog.setIp(ip);
        loginLog.setUserAgent(userAgent);
        sysUserLoginLogMapper.insert(loginLog);
    }

    public SysUser getItem(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }


    public PageResult<SysUserDTO> page(String keyword, String mobile, PageParam pageParam) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        Example.Criteria criteria1 = example.createCriteria();
        example.orderBy("sort").desc();
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andEqualTo("mobile", mobile);
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
            criteria1.orLike("username", keyword);
            criteria1.orLike("nickName", keyword);
            criteria1.orLike("name", keyword);
        }
        example.and(criteria1);
        // FIXME mybatis-plus 查询方式 解决 通用mapper username 写死困境
//        Page page = new Page(pageParam.getPageNum(), pageParam.getPageSize());
//        LambdaQueryWrapper<SysUser> lambda = new QueryWrapper<SysUser>().lambda();
//        lambda.and(e -> e.like(SysUser::getUsername,"%"+keyword+"%").or().like(SysUser::getNickName,"%"+keyword+"%"));
//        Page page1 = userMapper.selectPage(page, lambda);

        PageInfo<SysUser> userPages = PageHelper.startPage(pageParam)
                .doSelectPageInfo(() -> sysUserMapper.selectByExample(example));
        return PageResult.from(userPages, SysUserDTO.class);

    }


    public int update(Long id, SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        sysUser.setId(id);
//        SysUser rawAdmin = sysUserMapper.selectByPrimaryKey(id);
        //TODO 设置值
        int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return count;
    }


    public int delete(Long id) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        sysUser.setEnable(false);
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }


    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        Example example = new Example(SysUserRole.class);
        example.createCriteria().andEqualTo("userId", adminId);
        sysUserRoleMapper.deleteByExample(example);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                SysUserRole roleRelation = new SysUserRole();
                roleRelation.setUserId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            sysUserRoleMapper.insertList(list);
        }
        return count;
    }


    public List<SysRole> getRoleList(Long adminId) {
        return sysUserRoleDao.getRoleList(adminId);
    }


    public List<SysPermission> getPermissionList(Long id) {
        return sysUserRoleDao.getPermissionList(id);
    }

}
