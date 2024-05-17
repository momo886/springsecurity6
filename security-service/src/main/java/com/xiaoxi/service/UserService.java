package com.xiaoxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxi.entity.User;

/**
 * @Author： momo
 * 
 */
public interface UserService extends IService<User> {
    void saveUser(User user);

}
