package com.tomato.engine.spring.util.starter;

import com.tomato.engine.spring.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * spring util 自动配置
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@AutoConfiguration
@Slf4j
public class SpringUtilAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("spring util 自动配置完成");
    }

    @Bean
    SpringContextUtil springContextUtil() {
        log.info("spring util 初始化");
        return new SpringContextUtil();
    }
}
