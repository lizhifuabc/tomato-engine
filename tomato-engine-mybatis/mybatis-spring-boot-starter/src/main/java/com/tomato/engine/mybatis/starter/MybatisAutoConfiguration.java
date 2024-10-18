package com.tomato.engine.mybatis.starter;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * mybatis 自动配置
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@AutoConfiguration
@Slf4j
public class MybatisAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("mybatis 自动配置");
    }
}
