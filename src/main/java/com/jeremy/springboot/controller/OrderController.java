package com.jeremy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeremy.springboot.common.Constants;
import com.jeremy.springboot.exception.ServiceException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.jeremy.springboot.common.Result;

import com.jeremy.springboot.service.IOrderService;
import com.jeremy.springboot.entity.Order;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jeremy
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private IOrderService orderService;



    //新增或更新
    @PostMapping()
    public Result save(@RequestBody Order order)  {
        return Result.success(orderService.MysaveOrUpdate(order));
    }

    @GetMapping
    public Result findAll(){
        return Result.success(orderService.list());
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(orderService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        boolean b ;
        try {
            b = orderService.removeById(id);
            return Result.success(b);
        } catch (Exception e) {
             throw new ServiceException(Constants.CODE_600,"删除失败，请检查数据关联关系！");
        }
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        boolean b ;
        try {
            b = orderService.removeBatchByIds(ids);
            return Result.success(b);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_600,"删除失败，请检查数据关联关系！");
        }
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String customerName
    ){
        IPage<Order> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("o_customer_name",customerName);
        queryWrapper.orderByDesc("o_id");
        return Result.success(orderService.page(page,queryWrapper));
    }

}

