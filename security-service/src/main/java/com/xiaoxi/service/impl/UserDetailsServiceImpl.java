package com.xiaoxi.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoxi.entity.Permission;
import com.xiaoxi.entity.SecurityUser;
import com.xiaoxi.entity.User;
import com.xiaoxi.except.BusinessException;
import com.xiaoxi.service.PermissionService;
import com.xiaoxi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author： momo
 *
 */

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserService userService;


    private final PermissionService permissionService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getBaseMapper().selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        List<Permission> permissionList = permissionService.listByUserId(user.getId());
        if (CollUtil.isEmpty(permissionList)) {
            throw new BusinessException("没有权限");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        permissionList.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getPath()));
        });
        SecurityUser securityUser = new SecurityUser(user.getUserName(), user.getPassword(), authorities);
        securityUser.setUserId(user.getId());
        securityUser.setUserId(user.getId());
        return securityUser;
    }
}
