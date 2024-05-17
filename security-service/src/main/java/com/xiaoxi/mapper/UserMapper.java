package com.xiaoxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoxi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author： momo
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
