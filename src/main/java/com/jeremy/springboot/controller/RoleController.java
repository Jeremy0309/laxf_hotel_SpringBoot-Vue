package com.jeremy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.jeremy.springboot.common.Result;

import com.jeremy.springboot.service.IRoleService;
import com.jeremy.springboot.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jeremy
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;



    //新增或更新
    @PostMapping()


    public Result save(@RequestBody Role role){
        return Result.success(roleService.saveOrUpdate(role));
    }

    @GetMapping
    public Result findAll(){
        return Result.success(roleService.list());
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        return Result.success(roleService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(roleService.removeBatchByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        IPage<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(page,queryWrapper));
    }

    // 更新 Role和 Menu的关系
    /***
     * 绑定角色和菜单的关系
     * @param roleId 角色id
     * @param menuIds 菜单id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result saveroleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success( );
    }
    @GetMapping("/roleMenu/{roleId}")
    public Result getroleMenu(@PathVariable Integer roleId){

        return Result.success( roleService.getRoleMenu(roleId));
    }

}

