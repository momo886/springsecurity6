package com.xiaoxi.controller;

import com.xiaoxi.common.Result;
import com.xiaoxi.config.MobileAuthenticationToken;
import com.xiaoxi.entity.SecurityUser;
import com.xiaoxi.req.LoginReq;
import com.xiaoxi.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author： momo
 *
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Result login(@RequestBody LoginReq loginReq) {
        Authentication authenticationRequest=null;
        // 1手机验证码登录
        if(loginReq.getType()==1){
            authenticationRequest= new MobileAuthenticationToken(loginReq.getUsername(),loginReq.getCode());
        }else{
            authenticationRequest =new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());
        }

        Authentication authentication = this.authenticationManager.authenticate(authenticationRequest);
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> grantedAuthoritys = securityUser.getAuthorities();
        List<String> authorities = grantedAuthoritys.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Long userId = securityUser.getUserId();
        Map<String, Object> claims = Map.of("userId", userId, "username", securityUser.getUsername(),"authorities",authorities);
        String token = JwtUtils.createToken(claims);
        return Result.success(token);
    }


}
