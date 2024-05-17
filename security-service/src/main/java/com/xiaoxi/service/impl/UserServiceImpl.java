package com.xiaoxi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxi.entity.User;
import com.xiaoxi.mapper.UserMapper;
import com.xiaoxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author： momo
 * 
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

    }

}
