package com.tomato.engine.mybatis.sdk.replace;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;

import java.util.Arrays;

/**
 * sql 替换
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Slf4j
public class SqlReplace {
    /**
     * 替换SQL语句。
     *
     * @param invocation 拦截器调用对象，包含被拦截方法的参数等信息
     * @param newSql       新的SQL语句，用于替换原有的SQL语句
     */
    public static void replace(Invocation invocation, String newSql){
        // 获取当前执行的SQL语句
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args[1];
        // 生成新sql
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), newSql, boundSql.getParameterMappings(), parameter);
        log.debug("sql 分析拦截器 - sql replace old:{}",boundSql.getSql());
        log.debug("sql 分析拦截器 - sql replace new:{}",newSql);

        boundSql.getParameterMappings().forEach((e) -> {
            String prop = e.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        });

        // 把新的查询放到statement里
        MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql),null);
        args[0] = newMs;
    }


    /**
     * 替换sql，生成新的 MappedStatement
     *
     * @param ms          原MappedStatement对象
     * @param newSqlSource 新的SqlSource对象，用于替换原MappedStatement中的SqlSource
     * @param parameterMap 新的ParameterMap对象，用于替换原MappedStatement中的ParameterMap，如果为null则使用原MappedStatement的ParameterMap
     * @return 生成的新MappedStatement对象
     */
    public static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, ParameterMap parameterMap) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());

        // 处理主键属性
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            StringBuilder keyPropertiesBuilder = new StringBuilder();
            String[] keyProperties = ms.getKeyProperties();
            Arrays.stream(keyProperties).forEach(key -> keyPropertiesBuilder.append(key).append(","));
            keyPropertiesBuilder.delete(keyPropertiesBuilder.length() - 1, keyPropertiesBuilder.length());
            builder.keyProperty(keyPropertiesBuilder.toString());
        }

        builder.timeout(ms.getTimeout());

        // 使用提供的parameterMap，或回退到原始的parameterMap
        builder.parameterMap(parameterMap != null ? parameterMap : ms.getParameterMap());

        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }



    private record BoundSqlSqlSource(BoundSql boundSql) implements SqlSource {

        @Override
            public BoundSql getBoundSql(Object parameterObject) {
                return boundSql;
            }
        }
}
