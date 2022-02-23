package com.jeremy.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.jeremy.springboot.common.Result;
import com.jeremy.springboot.entity.User;
import com.jeremy.springboot.service.IUserService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class echartsControlller {
    @Autowired
    IUserService userService;

    @GetMapping("/example")
    public Result get(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x",new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
        map.put("y",new int[]{150, 230, 224, 218, 135, 147, 260});

        return Result.success(map);
    }
    @GetMapping("/members")
    public Result members(){
        List<User> list = userService.list();
        int q1 = 0,q2 = 0,q3 = 0,q4 = 0;//季度
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1: q1 += 1;break;
                case Q2: q2 += 1;break;
                case Q3: q3 += 1;break;
                case Q4: q4 += 1;break;
                default:break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));//相当于new ArrayList， 然后 。add(q1),.add(q2)....
    }

}
