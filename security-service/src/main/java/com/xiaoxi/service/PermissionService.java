package com.xiaoxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoxi.entity.Permission;
import com.xiaoxi.entity.User;

import java.util.List;

/**
 * @Author： momo
 *
 */

public interface PermissionService  extends IService<Permission> {
    List<Permission> listByUserId(Long id);
}
