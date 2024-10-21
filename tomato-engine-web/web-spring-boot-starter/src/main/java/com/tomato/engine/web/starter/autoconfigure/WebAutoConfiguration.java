package com.tomato.engine.web.starter.autoconfigure;

import com.tomato.engine.web.sdk.config.CorsFilterConfig;
import com.tomato.engine.web.sdk.config.RequestLoggingConfig;
import com.tomato.engine.web.sdk.interceptor.RequestWrapperFilter;
import com.tomato.engine.web.sdk.interceptor.ResponseWrapperFilter;
import com.tomato.engine.web.sdk.interceptor.XssHttpServletFilter;
import com.tomato.engine.web.sdk.properties.CryptoProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@Import({RequestLoggingConfig.class, CorsFilterConfig.class})
@EnableConfigurationProperties({CryptoProperties.class})
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

    @Bean
    XssHttpServletFilter xssHttpServletFilter() {
        return new XssHttpServletFilter();
    }
}
