package com.tomato.engine.idempotent.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author lizhifu
 * @since 2024/10/15
 */
@SpringBootApplication
public class IdempotentApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdempotentApplication.class, args);
    }
}
