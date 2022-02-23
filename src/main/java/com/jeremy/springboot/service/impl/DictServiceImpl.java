package com.jeremy.springboot.service.impl;

import com.jeremy.springboot.entity.Dict;
import com.jeremy.springboot.mapper.DictMapper;
import com.jeremy.springboot.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-21
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
