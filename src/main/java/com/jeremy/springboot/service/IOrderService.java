package com.jeremy.springboot.service;

import com.jeremy.springboot.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-24
 */
public interface IOrderService extends IService<Order> {

    Object MysaveOrUpdate(Order order);
}
