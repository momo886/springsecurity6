package com.xiaoxi.req;

import lombok.Data;

/**
 * @Author： momo
 *
 */
@Data
public class LoginReq {

    private Integer type;

    private String username;

    private String password;

    private String code;
}
