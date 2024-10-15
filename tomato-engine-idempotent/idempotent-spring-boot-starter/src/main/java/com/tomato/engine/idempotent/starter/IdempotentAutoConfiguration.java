package com.tomato.engine.idempotent.starter;

import com.tomato.engine.idempotent.sdk.aspect.IdempotentAspect;
import com.tomato.engine.idempotent.sdk.properties.IdempotentProperties;
import com.tomato.engine.idempotent.sdk.service.KeyResolverService;
import com.tomato.engine.idempotent.sdk.service.impl.DefaultKeyResolverServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * idempotent 自动配置
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@AutoConfiguration
@Slf4j
@EnableConfigurationProperties(IdempotentProperties.class)
public class IdempotentAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("idempotent 自动配置");
    }


    /**
     * 注册幂等切面Bean
     *
     * @return 返回幂等切面实例
     */
    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }

    /**
     * 创建一个KeyResolverService Bean，当Spring容器中没有找到KeyResolverService类型的Bean时，此Bean将被创建。
     *
     * @return 返回一个默认的KeyResolverServiceImpl实例。
     */
    @Bean
    @ConditionalOnMissingBean(KeyResolverService.class)
    public KeyResolverService keyResolverService() {
        return new DefaultKeyResolverServiceImpl();
    }
}
