package com.csun.mall.service;


import com.csun.mall.domain.*;
import com.csun.mall.mapper.SysRoleDao;
import com.csun.mall.mapper.SysRoleMapper;
import com.csun.mall.mapper.SysRoleMenuMapper;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 后台角色管理Service
 * Created by macro on 2018/9/30.
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 添加角色
     */
    public int create(SysRole role){

        role.setCreateTime(new Date());
        role.setUserCount(0);
        role.setSort(0);
        return sysRoleMapper.insert(role);
    }

    /**
     * 修改角色信息
     */
    public int update(Long id, SysRole role){
        role.setId(id);
        role.setCreateTime(new Date());
        return sysRoleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 批量删除角色
     */
    public int delete(List<Long> ids){
        Example example = new Example(SysRole.class);
        example.createCriteria().andIn("id",ids);
        int count = sysRoleMapper.deleteByExample(example);
        return count;
    }

    /**
     * 获取指定角色权限
     */
    public List<SysPermission> getPermissionList(Long roleId){
        return null;
    }

    /**
     * 修改指定角色的权限
     */
    @Transactional
    public int updatePermission(Long roleId, List<Long> permissionIds){
        return 0;
    }

    /**
     * 获取所有角色列表
     */
    public List<SysRole> list(){
        return sysRoleMapper.selectAll();
    }

    /**
     * 分页获取角色列表
     */
    public PageResult list(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> list= sysRoleMapper.selectAll();
        return PageResult.from(list);
    }

    /**
     * 根据管理员ID获取对应菜单
     */
    public List<SysMenu> getMenuList(Long adminId){
        return sysRoleDao.getMenuList(adminId);
    }

    /**
     * 获取角色相关菜单
     */
    public List<SysMenu> listMenu(Long roleId){
        return sysRoleDao.getMenuListByRoleId(roleId);
    }

    /**
     * 获取角色相关资源
     */
    public List<SysResource> listResource(Long roleId){
        return null;
    }

    /**
     * 给角色分配菜单
     */
    @Transactional
    public int allocMenu(Long roleId, List<Long> menuIds){
        //先删除原有关系
        Example example=new Example(SysRoleMenu.class);
        example.createCriteria().andEqualTo("roleId",roleId);
        sysRoleMenuMapper.deleteByExample(example);
        //批量插入新关系
        for (Long menuId : menuIds) {
            SysRoleMenu relation = new SysRoleMenu();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            sysRoleMenuMapper.insert(relation);
        }
        return menuIds.size();
    }

    /**
     * 给角色分配资源
     */
    @Transactional
    public int allocResource(Long roleId, List<Long> resourceIds){
        return 0;
    }
}
