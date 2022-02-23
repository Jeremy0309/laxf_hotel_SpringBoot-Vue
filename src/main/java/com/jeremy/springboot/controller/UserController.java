package com.jeremy.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeremy.springboot.common.Constants;
import com.jeremy.springboot.common.Result;
import com.jeremy.springboot.controller.vo.UserVo;
import com.jeremy.springboot.exception.ServiceException;
import com.jeremy.springboot.utils.TokenUtils;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import com.jeremy.springboot.service.IUserService;
import com.jeremy.springboot.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jeremy
 * @since 2022-02-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    //登录 ,@RequestBody 将前端传过来的json转为Java对象
    @PostMapping("/login")
    public Result login(@RequestBody UserVo userVo){
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if (StrUtil.isBlank(username) && StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数不足");
        }
        UserVo vo = userService.login(userVo);
        return  Result.success(vo);
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody UserVo userVo){
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if (StrUtil.isBlank(username) && StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        User user = userService.register(userVo);
        return Result.success(user);
    }
    //获取用户信息
    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User one = userService.getOne(queryWrapper);
        return Result.success(one);
    }

    //新增或更新
    @PostMapping()
    public Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }

    @GetMapping
    public Result findAll(){
        return Result.success(userService.list());
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        return  Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeBatchByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address
                                ){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
//        queryWrapper.and(w->w.like("address",address));
        queryWrapper.orderByDesc("id");
//        //获取当前用户信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("获取当前用户信息 =========== "+ currentUser.getNickname());
        return Result.success(userService.page(page,queryWrapper));
    }

    /**
     * 检查token
     * @param token
     */
    @PostMapping("/check")
    public Result check(@RequestBody String token){
        System.out.println("============== token =======:" +token);
    //校验token
        try {
            DecodedJWT decodedJWT = TokenUtils.verifyToken(token);
            return Result.success(Constants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Constants.CODE_401,"token验证失败！请重新登陆");
        }

    }
    /**
     * 导出接口
     */
    @GetMapping("/export")
    public Result export(@RequestParam(defaultValue = "")String token, HttpServletResponse response) throws Exception {
        try {
            TokenUtils.verifyToken(token);
            //从数据库获取数据
            List<User> list = userService.list();
            // 通过工具类创建writer
//        ExcelWriter writer = ExcelUtil.getWriter("d:/writeBeanTest.xlsx");
            //在内存操作，写到浏览器
            ExcelWriter writer = ExcelUtil.getWriter(true);

            //自定义标题别名
            writer.addHeaderAlias("username", "用户名");
            writer.addHeaderAlias("password", "密码");
            writer.addHeaderAlias("nickname", "昵称");
            writer.addHeaderAlias("email", "邮箱");
            writer.addHeaderAlias("phone", "电话");
            writer.addHeaderAlias("address", "地址");
            writer.addHeaderAlias("createTime", "创建时间");
            writer.addHeaderAlias("avatarUrl", "图片url");

// 合并单元格后的标题行，使用默认标题样式
//        writer.merge(4, "一班成绩单");

            // 一次性写出内容，使用默认样式，强制输出标题
            writer.write(list, true);

            //浏览器写出格式参考,固定写法
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            String fileName = URLEncoder.encode("用户信息", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            ServletOutputStream out = response.getOutputStream();
            writer.flush(out,true);//将writer里的内容刷新到out里
            out.close();
// 关闭writer，释放内存
            writer.close();
            return Result.success();
        } catch (Exception e) {
//            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 导入接口
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(in);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> lists = reader.read(1);
        List<User> list = new ArrayList();
        for (List<Object> row : lists) {
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            list.add(user);
        }
//        System.out.println(list);
        return Result.success(userService.saveBatch(list));
    }
}

