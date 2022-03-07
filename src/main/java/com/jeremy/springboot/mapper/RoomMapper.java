package com.jeremy.springboot.mapper;

import com.jeremy.springboot.controller.vo.RoomAvaVo;
import com.jeremy.springboot.entity.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jeremy
 * @since 2022-02-23
 */
public interface RoomMapper extends BaseMapper<Room> {


    List<Room> avaRooms(String startDate, String endDate);
}

