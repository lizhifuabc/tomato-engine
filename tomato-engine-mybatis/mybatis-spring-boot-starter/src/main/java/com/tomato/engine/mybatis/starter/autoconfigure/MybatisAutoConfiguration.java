package com.tomato.engine.mybatis.starter.autoconfigure;

import com.tomato.engine.mybatis.sdk.interceptor.SqlAnalysisInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

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

    @Bean
    SqlAnalysisInterceptor sqlAnalysisInterceptor() {
        log.info("mybatis sql 分析拦截器");
        return new SqlAnalysisInterceptor();
    }
}
