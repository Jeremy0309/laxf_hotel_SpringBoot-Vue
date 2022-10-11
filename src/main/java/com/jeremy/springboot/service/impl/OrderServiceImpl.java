package com.jeremy.springboot.service.impl;

import com.jeremy.springboot.entity.Order;
import com.jeremy.springboot.entity.RoomAvailability;
import com.jeremy.springboot.mapper.OrderMapper;
import com.jeremy.springboot.mapper.RoomAvailabilityMapper;
import com.jeremy.springboot.mapper.RoomMapper;
import com.jeremy.springboot.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private RoomAvailabilityMapper rAmapper;

    @Resource
    private RoomMapper roomMapper;

    @Override
    @Transactional
    public Object MysaveOrUpdate(Order order) {
        boolean b;
        //如果order只是更新，那就要先删除
        if (order.getId()==null){
            //新增订单
            //先更新order表
            b = saveOrUpdate(order);
            if (b){
                // 更新 sys_room_availability 表
                Date checkinTime = order.getCheckinTime();
                Date checkoutTime = order.getCheckoutTime();
                int days = getDays(checkinTime, checkoutTime);
                Date occupiedDate;
                for (int i = 0; i < days ; i++){
                    Calendar calendar   =   new GregorianCalendar();
                    calendar.setTime(checkinTime);
                    calendar.add(calendar.DATE,i);// + i
                    occupiedDate = calendar.getTime();//这个时间就是结果
                    RoomAvailability roomAvailability = new RoomAvailability();
                    roomAvailability.setDateOccupied(occupiedDate);
                    roomAvailability.setOrderId(order.getId());
                    roomAvailability.setRoomId(order.getRoomId());
                    roomAvailability.setRoomName(roomMapper.selectById(order.getRoomId()).getName());
                    rAmapper.insert(roomAvailability );
                }
            }
        }else{
            //修改订单
            b = saveOrUpdate(order);
        }
        return b;
    }
    /**
     * 获得两个日期之间的天数
     * @param fromDate 起始日期
     * @param toDate 结束日期
     * @return
     */
    public int getDays(Date fromDate, Date toDate){
        int oneDay = 1000 * 60 * 60 * 24 ;// day
        long inTime = fromDate.getTime();// ms
        long outTime = toDate.getTime();
        int startday = (int) Math.floor(inTime / oneDay);
        int endday = (int) Math.floor(outTime / oneDay);
        return endday-startday;
    }
}
