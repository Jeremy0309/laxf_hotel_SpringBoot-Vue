package com.jeremy.springboot.service;

import com.jeremy.springboot.controller.vo.RoomAvaVo;
import com.jeremy.springboot.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-23
 */
public interface IRoomService extends IService<Room> {

    List<Room> findAvaRooms(String startDate, String endDate);
}
