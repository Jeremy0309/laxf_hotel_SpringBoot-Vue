package com.jeremy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeremy.springboot.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.jeremy.springboot.common.Result;

import com.jeremy.springboot.service.IRoomAvailabilityService;
import com.jeremy.springboot.entity.RoomAvailability;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * order_id用于退房时候根据其来删除房间占用记录		订单表，sys_order，                    主表	可用表，sys_room_availability ，从表 前端控制器
 * </p>
 *
 * @author jeremy
 * @since 2022-02-25
 */
@RestController
@RequestMapping("/room-availability")
public class RoomAvailabilityController {

    @Resource
    private IRoomAvailabilityService roomAvailabilityService;

    //新增或更新
    @PostMapping()
    public Result save(@RequestBody RoomAvailability roomAvailability){
        return Result.success(roomAvailabilityService.saveOrUpdate(roomAvailability));
    }
    //取消订单：删除room-availability相应记录
    @DeleteMapping("/cancel")
    public Result cancelByOrderId(@RequestParam Integer orderId,@RequestParam Integer roomId){
        //根据订单id 删除roomAvailability表中 订单对应的记录
        int res = roomAvailabilityService.cancelByOrderId(orderId, roomId);
        if (res>0){
            return Result.success(res);
        }else{
            return Result.error(Constants.CODE_600,"此订单已取消入住过了！");
        }
    }

    @GetMapping
    public Result findAll(){
        return Result.success(roomAvailabilityService.list());
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roomAvailabilityService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        return Result.success(roomAvailabilityService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(roomAvailabilityService.removeBatchByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize
                                    ){
        IPage<RoomAvailability> page = new Page<>(pageNum,pageSize);
        QueryWrapper<RoomAvailability> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(roomAvailabilityService.page(page,queryWrapper));
    }
}

