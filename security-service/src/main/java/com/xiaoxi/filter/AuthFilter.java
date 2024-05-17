package com.xiaoxi.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.xiaoxi.entity.SecurityUser;
import com.xiaoxi.entity.User;
import com.xiaoxi.service.impl.UserDetailsServiceImpl;
import com.xiaoxi.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author： momo
 *
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            Claims claims = JwtUtils.claims(token);
            String username = String.valueOf(claims.get("username"));
            Long userId = Long.valueOf(claims.get("userId").toString());
            Object o = claims.get("authorities");
            JSONArray objects = JSONUtil.parseArray(o);
            Set<SimpleGrantedAuthority> authority=new HashSet<>();
            objects.forEach(ob->{
                authority.add(new SimpleGrantedAuthority(ob.toString()));
            });
            User user = new User();
            user.setId(userId);
            user.setUserName(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authority);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
