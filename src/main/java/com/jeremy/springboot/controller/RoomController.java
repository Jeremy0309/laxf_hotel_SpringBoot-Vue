package com.jeremy.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeremy.springboot.controller.vo.RoomAvaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.jeremy.springboot.common.Result;

import com.jeremy.springboot.service.IRoomService;
import com.jeremy.springboot.entity.Room;

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
@RequestMapping("/room")
public class RoomController {

    @Resource
    private IRoomService roomService;

    //新增或更新
    @PostMapping()
    public Result save(@RequestBody Room room){
        return Result.success(roomService.saveOrUpdate(room));
    }

    @GetMapping
    public Result findAll(){
        return Result.success(roomService.list());
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roomService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        return Result.success(roomService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(roomService.removeBatchByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String position
                           ){
        IPage<Room> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        if(!"".equals(name)){
            queryWrapper.like("r_name",name);
        }
        if(!"".equals(position)){
            queryWrapper.like("r_position",position);
        }
        queryWrapper.orderByDesc("r_id");
        return Result.success(roomService.page(page,queryWrapper));
    }
    @GetMapping("/avaRooms")
    public Result findAvaRooms(@RequestParam String startDate,
                               @RequestParam String endDate ){
//        IPage<Room> page = new Page<>(pageNum,pageSize);
        List<Room> roomlist = roomService.findAvaRooms(startDate, endDate);
        return Result.success(roomlist);
    }

}

