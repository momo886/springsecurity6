package com.xiaoxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author： momo
 *
 */
@TableName("t_permission")
@Data
public class Permission implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long permissionId;

    private String path;

}
