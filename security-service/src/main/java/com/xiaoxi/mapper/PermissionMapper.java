package com.xiaoxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxi.entity.Permission;
import com.xiaoxi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author： momo
 *
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("SELECT p.* FROM t_user_role ur " +
            "INNER JOIN t_role_permission rp ON ur.role_id=rp.role_id " +
            "INNER JOIN t_permission p ON rp.permission_id=p.permission_id WHERE ur.user_id=#{userId}")
    List<Permission> listByUserId(@Param("userId") Long userId);
}
