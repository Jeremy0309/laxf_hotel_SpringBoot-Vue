package com.jeremy.springboot.controller.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jeremy.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 接受前端登录请求的参数
 */

@Data
public class UserVo {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private List<Menu> menus;
    private String role;


}
