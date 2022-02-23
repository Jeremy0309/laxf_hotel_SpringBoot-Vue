package com.jeremy.springboot.service;

import com.jeremy.springboot.common.Result;
import com.jeremy.springboot.controller.vo.UserVo;
import com.jeremy.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-15
 */
public interface IUserService extends IService<User> {

    UserVo login(UserVo userVo);

    User register(UserVo userVo);

}
