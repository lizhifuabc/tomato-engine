package com.tomato.engine.mybatis.sdk.explain;

import com.tomato.engine.mybatis.sdk.constant.SqlConstant;
import com.tomato.engine.mybatis.sdk.extract.SqlExtractResult;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * sql explain 工具类
 *
 * @author lizhifu
 * @since 2024/10/17
 */
@Slf4j
public class SqlExplain {
    /**
     * 执行SQL语句的EXPLAIN操作，返回SQL执行计划的结果集
     *
     * @param sqlExtractResult sql提取结果，包含需要执行EXPLAIN的原始SQL语句
     * @param connection 数据库连接对象
     * @return 包含SQL执行计划结果集的列表
     */
    public static List<SqlExplainResult> explain(SqlExtractResult sqlExtractResult, Connection connection) {

        List<SqlExplainResult> sqlExplainResultList = new ArrayList<>();
        String sourceSql = sqlExtractResult.getSourceSql();
        // 验证 sourceSql 是否为安全的查询
        if (!isValidSql(sourceSql)) {
            log.debug("sql 分析拦截器 sourceSql 不合法或不支持执行 EXPLAIN: {}", sourceSql);
            return sqlExplainResultList;
        }

        String explainSql = SqlConstant.EXPLAIN + sourceSql;

        try (PreparedStatement preparedStatement = connection.prepareStatement(explainSql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                SqlExplainResult sqlExplainResult = new SqlExplainResult();
                sqlExplainResult.setSqlExtractResult(sqlExtractResult);
                convert(sqlExplainResult,rs);
                sqlExplainResultList.add(sqlExplainResult);
            }
        } catch (SQLException e) {
            log.error("sql 分析拦截器 执行 EXPLAIN 异常: {}", sourceSql, e);
        }
        log.debug("sql 分析拦截器 执行 EXPLAIN 结果: {}", sqlExplainResultList);
        return sqlExplainResultList;
    }

    /**
     * 将ResultSet对象转换为SqlExplainResult对象
     * ResultSet 不会为 null
     * @param resultSet 从数据库查询结果中获得的ResultSet对象
     * @param sqlExplainResult 转换后的SqlExplainResult对象
     * @throws SQLException 如果从ResultSet获取数据时发生数据库异常，则抛出SQLException
     */
    private static void convert(SqlExplainResult sqlExplainResult, ResultSet resultSet) throws SQLException {
        // 从 ResultSet 中获取并设置各字段的值
        sqlExplainResult.setId(resultSet.getLong("id"));
        sqlExplainResult.setSelectType(resultSet.getString("select_type"));
        sqlExplainResult.setTable(resultSet.getString("table"));
        sqlExplainResult.setType(resultSet.getString("type"));
        sqlExplainResult.setPossibleKeys(resultSet.getString("possible_keys"));
        sqlExplainResult.setKey(resultSet.getString("key"));
        sqlExplainResult.setKeyLen(resultSet.getString("key_len"));
        sqlExplainResult.setRef(resultSet.getString("ref"));
        sqlExplainResult.setRows(resultSet.getLong("rows"));
        sqlExplainResult.setExtra(resultSet.getString("Extra"));
    }

    /**
     * 简单验证 SQL 查询语句，确保只执行 SELECT 语句
     * @param sql 原始 SQL 语句
     * @return true 表示 SQL 合法，false 表示不合法
     */
    private static boolean isValidSql(String sql) {
        // 只允许 SELECT 开头的查询，防止恶意的其他类型语句
        String trimmedSql = sql.trim().toUpperCase();
        return trimmedSql.startsWith(SqlConstant.SELECT);
    }
}

