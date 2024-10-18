package com.tomato.engine.mybatis.sdk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * sql 分析配置
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Data
@ConfigurationProperties(prefix = "tomato.mybatis.sql.analysis")
public class SqlAnalysisProperties {
    /**
     * 分析开关，默认关闭
     */
    private Boolean analysisSwitch = false;

    /**
     * sql替换开关，默认关闭
     */
    private Boolean replaceSwitch = false;

    /**
     * 一个id 只检查一次，默认开启
     */
    private Boolean onlyCheckOnce = true;

    /**
     * 两次检查间隔 默认 5分钟
     */
    private Long checkInterval = 5 * 60 * 1000L;

    /**
     * 例外sql id，集合
     */
    private List<String> exceptSqlIds = new ArrayList<>();

    /**
     * 进行分析的sql类型
     */
    private List<String> sqlType = new ArrayList<>();
}
