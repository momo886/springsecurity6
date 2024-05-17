package com.xiaoxi.controller;

import com.xiaoxi.common.Result;
import com.xiaoxi.entity.User;
import com.xiaoxi.except.BusinessException;
import com.xiaoxi.service.UserService;
import com.xiaoxi.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author： momo
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public Result list() {
        List<User> list = userService.list();
        return Result.success(list);
    }

    @GetMapping("/get")
    public Result get() {
        Long userId = TokenUtils.getUserId();
        User user = userService.getById(userId);
        return Result.success(user);
    }


    @PostMapping("/add")
    public Result save(User user) {

        userService.saveUser(user);
        return Result.success();
    }


}
