package com.jeremy.springboot.service;

import com.jeremy.springboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-21
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
