package com.tomato.engine.jackson.starter.autoconfigure;

import com.tomato.engine.jackson.sdk.datamasking.DataMaskingService;
import com.tomato.engine.jackson.sdk.datamasking.impl.DefaultDataMaskingServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * jackson 自动配置
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@AutoConfiguration
@Slf4j
public class JacksonAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("jackson 自动配置");
    }

    @Bean
    @ConditionalOnMissingBean(DataMaskingService.class)
    public DataMaskingService dataMaskingService() {
        log.warn("数据脱敏服务暂未实现-->type-->{}", "default");
        return new DefaultDataMaskingServiceImpl();
    }
}
