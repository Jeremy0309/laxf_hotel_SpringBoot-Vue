package com.jeremy.springboot.service;

import com.jeremy.springboot.entity.RoomAvailability;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * order_id用于退房时候根据其来删除房间占用记录		订单表，sys_order，                    主表	可用表，sys_room_availability ，从表 服务类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-25
 */
public interface IRoomAvailabilityService extends IService<RoomAvailability> {
    public int cancelByOrderId(Integer orderId, Integer roomId);
}
