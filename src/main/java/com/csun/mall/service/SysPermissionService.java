package com.csun.mall.service;


import com.csun.mall.domain.SysPermission;
import com.csun.mall.mapper.SysPermissionMapper;
import com.csun.mall.pojo.dto.SysPermissionNode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;
    /**
     * 添加权限
     */
    public int create(SysPermission permission){
        permission.setEnable(true);
        permission.setCreateTime(new Date());
        permission.setSort(0);
        return sysPermissionMapper.insert(permission);
    }

    /**
     * 修改权限
     */
    public int update(Long id,SysPermission permission){
        permission.setId(id);
        return sysPermissionMapper.updateByPrimaryKeySelective(permission);
    }

    /**
     * 批量删除权限
     */
    public int delete(List<Long> ids){

        Example example = new Example(SysPermission.class);
        example.createCriteria().andIn("id",ids);
        return sysPermissionMapper.deleteByExample(example);
    }

    /**
     * 以层级结构返回所有权限
     */
    public List<SysPermissionNode> treeList(){
        List<SysPermission> permissionList = sysPermissionMapper.selectByExample(new Example(SysPermission.class));
        List<SysPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission,permissionList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 获取所有权限
     */
    public List<SysPermission> list(){
        return sysPermissionMapper.selectByExample(new Example(SysPermission.class));
    }

    private SysPermissionNode covert(SysPermission permission,List<SysPermission> permissionList){
        SysPermissionNode node = new SysPermissionNode();
        BeanUtils.copyProperties(permission,node);
        List<SysPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission,permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
