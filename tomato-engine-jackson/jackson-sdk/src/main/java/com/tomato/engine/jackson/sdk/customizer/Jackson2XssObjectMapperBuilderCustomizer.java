package com.tomato.engine.jackson.sdk.customizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tomato.engine.jackson.sdk.constant.Jackson2OrderConstant;
import com.tomato.engine.jackson.sdk.deserializer.XssJsonDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


/**
 * xss 配置
 *
 * @author lizhifu
 * @since 2024/10/17
 */
@Deprecated
public class Jackson2XssObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new XssJsonDeserializer());
        // 使用 jacksonObjectMapperBuilder 来注册模块
        jacksonObjectMapperBuilder.modules(simpleModule);
    }

    @Override
    public int getOrder() {
        return Jackson2OrderConstant.XSS;
    }
}
