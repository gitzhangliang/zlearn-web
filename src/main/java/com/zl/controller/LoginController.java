package com.zl.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zl.annotation.OriginalControllerReturnValue;
import com.zl.domain.Coder;
import com.zl.exception.BasicException;
import com.zl.exception.CoderException;
import com.zl.model.request.CoderDto;
import com.zl.repository.CoderRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zl
 * @date 2018/12/25.
 */
@RestController
@RequestMapping("/home")
public class LoginController {
    @Resource
    private CoderRepository coderRepository;

    @PostMapping("/login")
    @OriginalControllerReturnValue
    public Object login(@RequestBody CoderDto coder){
        Coder c = coderRepository.findByName(coder.getName());
        if(c != null){
            return JWT.create().withClaim("name",c.getName())
                    .sign( Algorithm.HMAC256(c.getName()));
        }
        throw new CoderException("用户不存在");
    }

    @GetMapping("/isLogin")
    public boolean isLogin(){
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Date date = new Date(System.currentTimeMillis() + 5000);
        String token = JWT.create().withClaim("name", "12").withExpiresAt(date).sign(Algorithm.HMAC256("12"));
        System.out.println(token);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("12")).build();

        try {
            jwtVerifier.verify(token);
            System.out.println("对了  1");
        } catch (JWTVerificationException e) {
            System.out.println("错了 1");
        }
        Thread.sleep(10000);
        try {
            jwtVerifier.verify(token);
            System.out.println("对了  2");

        } catch (JWTVerificationException e) {
            System.out.println("错了 2");
        }
        System.out.println(JWT.decode(token).getClaim("name").asString());
    }
}
