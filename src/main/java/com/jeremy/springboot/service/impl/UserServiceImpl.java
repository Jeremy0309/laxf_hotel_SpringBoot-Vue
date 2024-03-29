package com.jeremy.springboot.service.impl;


import cn.hutool.json.JSON;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeremy.springboot.common.Constants;
import com.jeremy.springboot.common.Result;
import com.jeremy.springboot.common.RoleEnum;
import com.jeremy.springboot.controller.vo.UserVo;
import com.jeremy.springboot.entity.Menu;
import com.jeremy.springboot.entity.User;
import com.jeremy.springboot.exception.ServiceException;
import com.jeremy.springboot.mapper.MenuMapper;
import com.jeremy.springboot.mapper.RoleMapper;
import com.jeremy.springboot.mapper.RoleMenuMapper;
import com.jeremy.springboot.mapper.UserMapper;
import com.jeremy.springboot.service.IMenuService;
import com.jeremy.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeremy.springboot.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeremy
 * @since 2022-02-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;

    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public UserVo login(UserVo userVo) {
        // 先从缓存获取数据，如果有则直接返回
        // 如果无，则查询mysql,并将数据设置到缓存
        String key = "username:" +userVo.getUsername();

        Object userObject = redisTemplate.opsForValue().get(key);
        if (userObject == null){
            synchronized (this.getClass()){
                userObject = redisTemplate.opsForValue().get(key);
                if (userObject == null){
                    log.debug("----> 查询数据库..............");
                    //查询数据库
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("username",userVo.getUsername());
                    queryWrapper.eq("password",userVo.getPassword());
                    User one = getUserInfo(queryWrapper);
                    if (one!=null){
                        BeanUtils.copyProperties(one,userVo);
                        // 设置token
                        String token = TokenUtils.genToken(one.getId().toString(), one.getPassword().toString());
                        userVo.setToken(token);
                        //UserVo获取菜单信息
                        String role = one.getRole();//ROLE_ADMIN
                        //获取角色对应的菜单
                        List<Menu> roleMenus = getRoleMenus(role);
                        //设置用户菜单列表
                        userVo.setMenus(roleMenus);

                        //redis set
                        redisTemplate.opsForValue().set(key,userVo);

                        return userVo;
                    }else{
                        throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
                    }
                }else{
                    log.debug("----> 查询缓存(同步代码块)>>>>>>>>>>>>>>>>>");
                    UserVo userVo1 = com.alibaba.fastjson.JSON.parseObject(com.alibaba.fastjson.JSON.toJSONString(userObject), UserVo.class);
                    return userVo1;
                }
            }
        }else {
            log.debug("----> 查询缓存>>>>>>>>>>>>>>>>>");
            UserVo userVo1 = com.alibaba.fastjson.JSON.parseObject(com.alibaba.fastjson.JSON.toJSONString(userObject), UserVo.class);
            return userVo1;
        }

//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",userVo.getUsername());
//        queryWrapper.eq("password",userVo.getPassword());
//        User one = getUserInfo(queryWrapper);
//        if (one!=null){
//            BeanUtils.copyProperties(one,userVo);
//            // 设置token
//            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword().toString());
//            userVo.setToken(token);
//            //UserVo获取菜单信息
//            String role = one.getRole();//ROLE_ADMIN
//            //获取角色对应的菜单
//            List<Menu> roleMenus = getRoleMenus(role);
//            //设置用户菜单列表
//            userVo.setMenus(roleMenus);
//            return userVo;
//        }else{
//            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
//        }
    }

    @Override
    public User register(UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVo.getUsername());
        User user = getUserInfo(queryWrapper);
        if (user == null){
            user = new User();
            BeanUtils.copyProperties(userVo,user);
            //默认角色为普通角色
            user.setRole(RoleEnum.ROLE_USER.toString());
            save(user);
            return user;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
    }

    @Override
    public boolean logout(String username) {
        String key = "username:" +username;
        Boolean delete = redisTemplate.delete(key);
        if(delete){
            log.debug("==============删除redis=======");
            return delete;
        }else{
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }


    public User getUserInfo(QueryWrapper queryWrapper){
        User user;
        try {
            user = getOne(queryWrapper);//查询到多条会报错
        } catch (Exception e) {
//            e.printStackTrace();
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return user;
    }

    /**
     * roleKey对应的菜单列表List<Menu>，【包含Menu父子关系】
     * @param roleKey
     * @return
     */
    private List<Menu> getRoleMenus(String roleKey){
        Integer roleId = roleMapper.selectByRoleKey(roleKey);
        //查出   roleId对应的所有菜单id
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);//[1,2,3,3..]

        //查出系统所有菜单
        List<Menu> menus = menuService.findMenus("");//[{menu1},{menu2}，{menu3}..]
        //用户实际菜单
        List<Menu> roleMenus = new ArrayList<>();
        //筛选当前用户角色菜单 （过滤）
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // 移除  List<Menu> children 中 menu.id 不在 menuIds中的 menu
            children.removeIf(child ->!menuIds.contains(child.getId()));//Predicate类接口
            /**
             * 相当于
             for (Menu child : children) {
             if (!menuIds.contains(child.getId())){
             children.remove(child);
             }
             }
             */
        }
        return roleMenus;
    }
}
