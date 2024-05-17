package com.xiaoxi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @Author： momo
 *
 */
@MapperScan("com.xiaoxi.mapper")
@AutoConfiguration
public class MyBatisConfig {
}
