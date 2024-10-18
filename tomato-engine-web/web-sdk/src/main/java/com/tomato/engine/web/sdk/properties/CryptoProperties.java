package com.tomato.engine.web.sdk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 接口加密、解密
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Data
@ConfigurationProperties(prefix = "web.crypto")
public class CryptoProperties {
    /**
     * 加密key
     */
    private String key;

    /**
     * 加密类型
     */
    private String type;
}
