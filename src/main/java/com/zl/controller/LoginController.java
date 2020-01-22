package com.zl.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zl.annotation.OriginalControllerReturnValue;
import com.zl.domain.Coder;
import com.zl.exception.CoderException;
import com.zl.model.request.CoderDto;
import com.zl.repository.CoderRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tzxx
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
}
