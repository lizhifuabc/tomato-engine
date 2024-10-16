package com.tomato.engine.web.sdk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * 日志记录
 * <p>
 * 需要配置日志级别为 DEBUG 级别
 * @author lizhifu
 * @since 2024/10/16
 */
@Configuration
public class RequestLoggingConfig {

    /**
     * 创建一个 CommonsRequestLoggingFilter Bean，用于在 Spring 应用中记录 HTTP 请求的日志。
     *
     * @return 配置好的 CommonsRequestLoggingFilter 实例
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        String beforeMessagePrefix = "请求之前 [";
        filter.setBeforeMessagePrefix(beforeMessagePrefix);
        String beforeMessageSuffix = "]";
        filter.setBeforeMessageSuffix(beforeMessageSuffix);
        String afterMessagePrefix = "请求之后 [";
        filter.setAfterMessagePrefix(afterMessagePrefix);
        String afterMessageSuffix = "]";
        filter.setAfterMessageSuffix(afterMessageSuffix);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(true);
        filter.setIncludeClientInfo(true);
        return filter;
    }
}
