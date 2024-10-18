package com.tomato.engine.mybatis.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@SpringBootApplication
@MapperScan("com.tomato.engine.mybatis.example.mapper")
public class MybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}
