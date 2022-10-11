package com.jeremy.springboot;

import cn.hutool.core.date.DateUnit;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jeremy.springboot.controller.vo.RoomAvaVo;
import com.jeremy.springboot.entity.Room;
import com.jeremy.springboot.service.IRoomService;
import com.jeremy.springboot.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class SpringbootApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    void test(){
//        String token = TokenUtils.genToken("1", "123456");
//        DecodedJWT decodedJWT = TokenUtils.verifyToken(token);
//        System.out.println("token:"+token);
//        System.out.println("Payload:"+decodedJWT.getPayload());
//        System.out.println("Signature:"+decodedJWT.getSignature());
//    }
//
//        //模拟并发
//    @Test
//    public static void TestRedis(String[] args) {
//        ExecutorService es = Executors.newFixedThreadPool(100);
//        for (int i = 0; i < 500;i++){
//            es.submit(new Runnable() {
//                @Override
//                public void run() {
//                    //要多次调用的方法
//                    System.out.println("访问");
//                }
//            });
//        }
//    }



}
