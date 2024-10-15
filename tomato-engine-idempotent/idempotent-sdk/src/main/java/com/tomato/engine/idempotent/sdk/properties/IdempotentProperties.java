package com.tomato.engine.idempotent.sdk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 幂等拦截配置
 *   <p>
 *   map-cache-key: "idempotent"
 *   <p>
 *   key-name: "key"
 *   <p>
 *   del-key-name: "delKey"
 *  <p>
 * @author lizhifu
 * @since 2024/10/15
 */
@Configuration
@ConfigurationProperties(prefix = "idempotent")
@Data
public class IdempotentProperties {
    /**
     * redis缓存key:用于标识 RMapCache 的键名称
     */
    private String redisCacheKey = "idempotent";

    /**
     * ThreadLocal 缓存中保存当前请求的幂等键的名称
     */
    private String keyName = "key";

    /**
     * ThreadLocal 缓存中保存是否需要删除幂等键的标志位
     */
    private String delKeyName = "delKey";
}
