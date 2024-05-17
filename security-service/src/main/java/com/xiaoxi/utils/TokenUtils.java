package com.xiaoxi.utils;

import com.xiaoxi.entity.SecurityUser;
import com.xiaoxi.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author： momo
 *
 */
public class TokenUtils {

    public static Long getUserId(){
        User securityUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityUser.getId();
    }

    public static String getUsername(){
        SecurityUser securityUser = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityUser.getUsername();
    }
}
