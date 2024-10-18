package com.tomato.engine.mybatis.sdk.extract;

import lombok.Data;

/**
 * sql 提取结果
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Data
public class SqlExtractResult {

    /**
     * 基于mybatis 配置的sql id
     */
    private String sqlId;

    /**
     * 待执行，原sql
     */
    private String sourceSql;
}
