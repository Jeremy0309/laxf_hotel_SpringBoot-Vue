package com.jeremy.springboot.service.impl;

import com.jeremy.springboot.controller.vo.RoomAvaVo;
import com.jeremy.springboot.entity.Room;
import com.jeremy.springboot.mapper.RoomMapper;
import com.jeremy.springboot.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-23
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Resource
    RoomMapper roomMapper;





    @Override
    public List<Room> findAvaRooms(String startDate, String endDate) {
        return roomMapper.avaRooms(startDate, endDate);
    }
}
