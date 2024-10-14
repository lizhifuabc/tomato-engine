package com.tomato.engine.easy.excel.starter;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * easy excel 自动配置
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@AutoConfiguration
@Slf4j
public class EasyExcelAutoConfiguration {
    @PostConstruct
    public void init() {
        log.info("easy excel 自动配置");
    }

}
