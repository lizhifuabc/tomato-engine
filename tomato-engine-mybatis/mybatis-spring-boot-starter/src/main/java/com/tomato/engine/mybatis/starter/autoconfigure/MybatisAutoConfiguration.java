package com.tomato.engine.mybatis.starter.autoconfigure;

import com.tomato.engine.mybatis.sdk.interceptor.SqlAnalysisInterceptor;
import com.tomato.engine.mybatis.sdk.properties.SqlAnalysisProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * mybatis 自动配置
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@AutoConfiguration
@EnableConfigurationProperties(SqlAnalysisProperties.class)
@Slf4j
public class MybatisAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("mybatis 自动配置");
    }

    @Bean
    @ConditionalOnProperty(name = "tomato.mybatis.sql.analysis.analysis-switch", havingValue = "true")
    SqlAnalysisInterceptor sqlAnalysisInterceptor(SqlAnalysisProperties sqlAnalysisProperties) {
        log.info("mybatis sql 分析拦截器");
        return new SqlAnalysisInterceptor(sqlAnalysisProperties);
    }
}
