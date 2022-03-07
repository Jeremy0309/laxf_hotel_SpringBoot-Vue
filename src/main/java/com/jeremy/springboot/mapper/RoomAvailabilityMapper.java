package com.jeremy.springboot.mapper;

import com.jeremy.springboot.entity.RoomAvailability;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * order_id用于退房时候根据其来删除房间占用记录		订单表，sys_order，                    主表	可用表，sys_room_availability ，从表 Mapper 接口
 * </p>
 *
 * @author jeremy
 * @since 2022-02-25
 */
public interface RoomAvailabilityMapper extends BaseMapper<RoomAvailability> {

}
