package com.xiaoxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxi.entity.Permission;
import com.xiaoxi.mapper.PermissionMapper;
import com.xiaoxi.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author： momo
 *
 */
@Service
@AllArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public List<Permission> listByUserId(Long userId) {
        return permissionMapper.listByUserId(userId);
    }
}
