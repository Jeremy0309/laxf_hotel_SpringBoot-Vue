package com.jeremy.springboot;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.jeremy.springboot.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        String token = TokenUtils.genToken("1", "123456");
        DecodedJWT decodedJWT = TokenUtils.verifyToken(token);
        System.out.println("token:"+token);
        System.out.println("Payload:"+decodedJWT.getPayload());
        System.out.println("Signature:"+decodedJWT.getSignature());
    }

}
