package com.jeremy.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeremy.springboot.entity.Menu;
import com.jeremy.springboot.entity.Role;
import com.jeremy.springboot.entity.RoleMenu;
import com.jeremy.springboot.mapper.RoleMapper;
import com.jeremy.springboot.mapper.RoleMenuMapper;
import com.jeremy.springboot.service.IMenuService;
import com.jeremy.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    IMenuService menuService;

    @Transactional//事务注解
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_id",roleId);
//        roleMenuMapper.delete(queryWrapper);
        //1. 先删除当前角色id所有绑定关系，
        roleMenuMapper.deletebyRoleId(roleId);
        HashSet<Integer> set = new HashSet<>();
        //2 再把前端传过来的菜单id数组绑定到当前的角色id上去
        for (Integer menuId : menuIds) {
            //判断 menuId的menu是否有pid
            Integer pid = menuService.getById(menuId).getPid();
            if ( pid !=null && !set.contains(pid) &&  !menuIds.contains(pid)){//二级菜单,且父级菜单id不在 menuIds中
                //添加此二级菜单对应的父级菜单id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(pid);
                roleMenuMapper.insert(roleMenu);
                set.add(pid);
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
