package com.jeremy.springboot.service.impl;

import com.jeremy.springboot.entity.Customer;
import com.jeremy.springboot.mapper.CustomerMapper;
import com.jeremy.springboot.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-23
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
