package com.jeremy.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeremy.springboot.common.Constants;
import com.jeremy.springboot.entity.Order;
import com.jeremy.springboot.entity.RoomAvailability;
import com.jeremy.springboot.exception.ServiceException;
import com.jeremy.springboot.mapper.OrderMapper;
import com.jeremy.springboot.mapper.RoomAvailabilityMapper;
import com.jeremy.springboot.service.IRoomAvailabilityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * order_id用于退房时候根据其来删除房间占用记录 服务实现类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-24
 */
@Service
public class RoomAvailabilityServiceImpl extends ServiceImpl<RoomAvailabilityMapper, RoomAvailability> implements IRoomAvailabilityService {

    @Resource
    RoomAvailabilityMapper roomAvailabilityMapper;
    @Resource
    OrderMapper orderMapper;

    @Override
    public int cancelByOrderId(Integer orderId, Integer roomId) {
        //根据订单id 删除roomAvailability表中 订单对应的记录
        QueryWrapper<RoomAvailability> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id",roomId);
        queryWrapper.eq("order_id", orderId);

        int  delete;
        try {
            delete = roomAvailabilityMapper.delete(queryWrapper);
            //删除 订单表 - 可用表关联成功，同时设置订单状态为 0
            Order order = orderMapper.selectById(orderId);
            order.setState(false);
            orderMapper.updateById(order);
            return delete;
        } catch (Exception e) {
             throw new  ServiceException(Constants.CODE_600,"取消订单失败");
        }

    }
}
