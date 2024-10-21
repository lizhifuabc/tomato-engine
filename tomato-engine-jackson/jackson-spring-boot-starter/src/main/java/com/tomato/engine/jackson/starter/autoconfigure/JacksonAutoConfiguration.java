package com.tomato.engine.jackson.starter.autoconfigure;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tomato.engine.jackson.sdk.datamasking.DataMaskingService;
import com.tomato.engine.jackson.sdk.datamasking.impl.DefaultDataMaskingServiceImpl;
import com.tomato.engine.jackson.sdk.deserializer.XssJsonDeserializer;
import com.tomato.engine.jackson.sdk.serializer.LongJsonSerializer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
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

    /**
     * 自定义Jackson的ObjectMapper配置
     * TODO @Enable 的方式进行注入
     *
     * @return 返回Jackson2ObjectMapperBuilderCustomizer对象，用于自定义Jackson的ObjectMapper配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        log.info("jackson 自定义配置");
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new XssJsonDeserializer());
        // 使用 jacksonObjectMapperBuilder 来注册模块
        return builder -> {
            log.info("jackson 自定义配置 Long 序列化");
            builder.serializerByType(Long.class, LongJsonSerializer.INSTANCE);
            log.info("jackson 自定义配置 xss 配置");
            builder.modules(simpleModule);
        };
    }
}
