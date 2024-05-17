package com.xiaoxi.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author： momo
 *
 */
@TableName("t_user")
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    @TableField("is_deleted")
    private Integer deleted;

}
