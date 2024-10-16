package com.tomato.engine.web.starter.autoconfigure;

import com.tomato.engine.web.sdk.config.RequestLoggingConfig;
import com.tomato.engine.web.sdk.config.RequestWrapperFilter;
import com.tomato.engine.web.sdk.config.ResponseWrapperFilter;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@AutoConfiguration
@Slf4j
@Import(RequestLoggingConfig.class)
public class WebAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("web 自动配置");
    }

    @Bean
    RequestWrapperFilter requestWrapperFilter() {
        return new RequestWrapperFilter();
    }

    @Bean
    ResponseWrapperFilter responseWrapperFilter() {
        return new ResponseWrapperFilter();
    }
}
