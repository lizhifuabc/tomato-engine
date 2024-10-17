package com.tomato.engine.mybatis.sdk.constant;

/**
 * sql 常量
 *
 * @author lizhifu
 * @since 2024/10/17
 */
public interface SqlConstant {

    /**
     * 执行SQL语句的EXPLAIN操作，返回SQL执行计划的结果集
     */
    String EXPLAIN = "EXPLAIN ";

    /**
     * SELECT
     */
    String SELECT = "SELECT ";
}
