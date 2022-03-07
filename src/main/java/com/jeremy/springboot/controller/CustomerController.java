package com.jeremy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.jeremy.springboot.common.Result;

import com.jeremy.springboot.service.ICustomerService;
import com.jeremy.springboot.entity.Customer;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jeremy
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    //新增或更新
    @PostMapping()
    public Result save(@RequestBody Customer customer){
        return Result.success(customerService.saveOrUpdate(customer));
    }

    @GetMapping
    public Result findAll(){
        return Result.success(customerService.list());
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(customerService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        return Result.success(customerService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(customerService.removeBatchByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String credentials,
                           @RequestParam(defaultValue = "") String phone
                                    ){
        IPage<Customer> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        if(!"".equals(name)){
            queryWrapper.like("c_name",name);
        }
        if(!"".equals(credentials)){
            queryWrapper.like("c_credentials",credentials);
        }
        if(!"".equals(phone)){
            queryWrapper.like("c_phone",phone);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(customerService.page(page,queryWrapper));
    }
}

